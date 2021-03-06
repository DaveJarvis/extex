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
 * The 'cmap' table maps character codes to glyph indices. The choice of
 * encoding for a particular font is dependent upon the conventions used by the
 * intended platform. A font intended to run on multiple platforms with
 * different encoding conventions will require multiple encoding tables. As a
 * result, the 'cmap' table may contain multiple subtables, one for each
 * supported encoding scheme.
 * <p>
 * Character codes that do not correspond to any glyph in the font should be
 * mapped to glyph index 0. At this location in the font there must be a special
 * glyph representing a missing character, typically a box. No character code
 * should be mapped to glyph index -1, which is a special value reserved in
 * processing to indicate the position of a glyph deleted from the glyph stream.
 * <p>
 * The 'cmap' table begins with an index containing the table version number
 * followed by the number of encoding tables. The encoding subtables follow.
 * <p>
 * The original definition of the 'cmap' table only allowed for mappings from
 * traditional character set standards, which used eight, a mixture of eight and
 * sixteen, or sixteen bits for each character. With the introduction of ISO/IEC
 * 10646-1 and the use of surrogates in versions of Unicode from 2.0 onwards, it
 * is possible that fonts may require references to data that uses a mixture of
 * sixteen and thirty-two or thirty-two bits per character.
 * <p>
 * It was originally suggested that a version number of 0 is used to indicate
 * that only encoding subtables of types 0 through 6 are present in the 'cmap'
 * table. If the 'cmap' table contains encoding subtables of types 8.0 or
 * higher, the version number would then be set to 1. These latter encoding
 * subtable types have been introduced to provide better support for Unicode
 * text encoded using surrogates.
 *
 * <table>
 * <caption>TBD</caption>
 * <tbody>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </tbody>
 * <tr>
 * <td>USHORT</td>
 * <td>Table version number (0).</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>Number of encoding tables, <i>n.</i></td>
 * </tr>
 * </table>
 * <p>
 * TODO incompelte
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TtfTableCMAP extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * Abstract class for all formats.
   */
  public abstract class Format implements XMLWriterConvertible {

    /**
     * format.
     */
    private final int format;

    /**
     * version.
     */
    private final int fversion;

    /**
     * length.
     */
    private final int length;

    /**
     * Create a new object.
     *
     * @param form format
     * @param rar  input
     * @throws IOException if an IO-error occurs
     */
    Format( int form, RandomAccessR rar ) throws IOException {

      format = form;
      length = rar.readUnsignedShort();
      fversion = rar.readUnsignedShort();
    }

    /**
     * Add the attrinbutes.
     *
     * @param writer The xml writer.
     * @throws IOException if an IO-error occurs.
     */
    public void addAttributes( XMLStreamWriter writer ) throws IOException {

      writer.writeAttribute( "version", fversion );
      writer.writeAttribute( "format", format );
      writer.writeAttribute( "length", length );
    }

    /**
     * Returns the format.
     *
     * @return Returns the format
     */
    public int getFormat() {

      return format;
    }

    /**
     * Returns the length.
     *
     * @return Returns the length
     */
    public int getLength() {

      return length;
    }

    /**
     * Returns the version.
     *
     * @return returns the version
     */
    public int getVersion() {

      return fversion;
    }

    /**
     * map char code.
     *
     * @param charCode the charcode
     * @return Returns the map char code
     */
    public abstract int mapCharCode( int charCode );

    /**
     * Returns the info for this class.
     *
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

      StringBuilder buf = new StringBuilder( "Cmap format\n" );
      buf.append( "   Version      : " + fversion + '\n' );
      buf.append( "   format       : " + format + '\n' );
      buf.append( "   length       : " + length + '\n' );
      return buf.toString();
    }
  }

  /**
   * Format 0 is suitable for fonts whose character codes and glyph indices
   * are restricted to a single byte.
   * <p>
   * It is the standard Apple character to glyph index mapping table.
   * </p>
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>format</td>
   * <td>Format number is set to 0.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>length</td>
   * <td>This is the length in bytes of the subtable.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>version</td>
   * <td>Version number (starts at 0).</td>
   * </tr>
   * <tr>
   * <td>BYTE</td>
   * <td>glyphIdArray[256]</td>
   * <td>An array that maps character codes to glyph index values.</td>
   * </tr>
   * </table>
   */
  public class Format0 extends Format {

    /**
     * Size of the glyph-id-array.
     */
    private static final int ARRAYSIZE = 256;

    /**
     * glyph id array.
     */
    private final byte[] glyphIdArray = new byte[ ARRAYSIZE ];

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format0( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT0, rar );
      for( int i = 0; i < glyphIdArray.length; i++ ) {
        glyphIdArray[ i ] = (byte) rar.readUnsignedByte();
      }
    }

    @Override
    public int mapCharCode( int charCode ) {

      if( 0 <= charCode && charCode < glyphIdArray.length ) {
        return glyphIdArray[ charCode ];
      }
      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );
      writer.writeStartElement( "mapcharcode" );
      for( int i = 0; i < 0xff; i++ ) {
        int c = mapCharCode( i );
        if( c > 0 ) {
          writer.writeStartElement( "entry" );
          writer.writeAttribute( "charcode", i );
          writer.writeAttribute( "value", c );
          writer.writeEndElement();
        }
      }
      writer.writeEndElement();
      writer.writeEndElement();
    }
  }

  /**
   * Format 10.0 is a bit like format 6, in that it defines a trimmed array
   * for a tight range of 32-bit character codes.
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td>Type</td>
   * <td>Name</td>
   * <td>Description</td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>Fixed32</td>
   * <td>format</td>
   * <td>Subtable format; set to 10.0</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>length</td>
   * <td>Byte length of this subtable (including the header)</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>language</td>
   * <td>0 if don't care</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>startCharCode</td>
   * <td>First character code covered</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>numChars</td>
   * <td>Number of character codes covered</td>
   * </tr>
   * <tr>
   * <td>UInt16</td>
   * <td>glyphs[]</td>
   * <td>Array of glyph indices for the character codes covered</td>
   * </tr>
   * </table>
   */
  public class Format10 extends Format {

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format10( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT10, rar );

    }

    @Override
    public int mapCharCode( int charCode ) {

      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );

      writer.writeEndElement();
    }

  }

  /**
   * Format 12.0 is a bit like format 4, in that it defines segments for
   * sparse representation in 4-byte character space.
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td>Type</td>
   * <td>Name</td>
   * <td>Description</td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>Fixed32</td>
   * <td>format</td>
   * <td>Subtable format; set to 12.0</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>length</td>
   * <td>Byte length of this subtable (including the header)</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>language</td>
   * <td>0 if don't care</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>nGroups</td>
   * <td>Number of groupings which follow</td>
   * </tr>
   * </table>
   */
  public class Format12 extends Format {

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format12( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT12, rar );

    }

    @Override
    public int mapCharCode( int charCode ) {

      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );

      writer.writeEndElement();
    }
  }

  // -------------------------------------------------------
  // -------------------------------------------------------

  /**
   * The format 2 mapping subtable type is used for fonts containing Japanese,
   * Chinese, or Korean characters. The code standards used in this table are
   * supported on Macintosh systems in Asia.
   * <p>
   * These fonts contain a mixed 8/16-bit encoding, in which certain byte
   * values are set aside to signal the first byte of a 2-byte character.
   * These special values are also legal as the second byte of a 2-byte
   * character.
   * </p>
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>format</td>
   * <td>Format number is set to 2.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>length</td>
   * <td>Length in bytes.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>version</td>
   * <td>Version number (starts at 0)</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>subHeaderKeys[256]</td>
   * <td>Array that maps high bytes to subHeaders: value is subHeader index *
   * 8.</td>
   * </tr>
   * <tr>
   * <td>4 words struct</td>
   * <td>subHeaders[ ]</td>
   * <td>Variable-length array of subHeader structures.</td>
   * </tr>
   * <tr>
   * <td>4 words-struct</td>
   * <td>subHeaders[ ]</td>
   * <td></td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>glyphIndexArray[ ]</td>
   * <td>Variable-length array containing subarrays used for mapping the low
   * byte of 2-byte characters.</td>
   * </tr>
   * </table>
   *
   * <p>
   * A subHeader is structured as follows:
   * </p>
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>firstCode</td>
   * <td>First valid low byte for this subHeader.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>entryCount</td>
   * <td>Number of valid low bytes for this subHeader.</td>
   * </tr>
   * <tr>
   * <td>SHORT</td>
   * <td>idDelta</td>
   * <td>See text below.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>idRangeOffset</td>
   * <td>
   * <p>
   * The firstCode and entryCount values specify a subrange that begins at
   * firstCode and has a length equal to the value of entryCount. This
   * subrange stays within the 0&ndash;255 range of the byte being mapped.
   * Bytes outside of this subrange are mapped to glyph index 0 (missing
   * glyph).The offset of the byte within this subrange is then used as index
   * into a corresponding subarray of glyphIndexArray. This subarray is also
   * of length entryCount. The value of the idRangeOffset is the number of
   * bytes past the actual location of the idRangeOffset word where the
   * glyphIndexArray element corresponding to firstCode appears.
   * </p>
   * <p>
   * Finally, if the value obtained from the subarray is not 0 (which
   * indicates the missing glyph), you should add idDelta to it in order to
   * get the glyphIndex. The value idDelta permits the same subarray to be
   * used for several different subheaders. The idDelta arithmetic is modulo
   * 65536.
   * </p>
   * </td>
   * </tr>
   * </table>
   */
  public class Format2 extends Format {

    // private short[] subHeaderKeys = new short[256];

    // private int[] subHeaders1;

    // private int[] subHeaders2;

    // private short[] glyphIndexArray;

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format2( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT2, rar );
    }

    @Override
    public int mapCharCode( int charCode ) {

      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );

      writer.writeEndElement();
    }

  }

  /**
   * Format 4 is a two-byte encoding format. It should be used when the
   * character codes for a font fall into several contiguous ranges, possibly
   * with holes in some or all of the ranges. That is, some of the codes in a
   * range may not be associated with glyphs in the font. Two-byte fonts that
   * are densely mapped should use Format 6.
   *
   * <p>
   * This is the Microsoft standard character to glyph index mapping table.
   * </p>
   * <p>
   * 1. A four-word header gives parameters for an optimized search of the
   * segment list;
   * </p>
   * <p>
   * 2. Four parallel arrays describe the segments (one segment for each
   * contiguous range of codes);
   * </p>
   * <p>
   * 3. A variable-length array of glyph IDs (unsigned words).
   * </p>
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>format</td>
   * <td>Format number is set to 4.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>length</td>
   * <td>Length in bytes.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>version</td>
   * <td>Version number (starts at 0).</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>segCountX2</td>
   * <td>2 x segCount.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>searchRange</td>
   * <td>2 x 2**floor(log 2 (segCount)))</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>entrySelector</td>
   * <td>2 (searchRange/2)</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>rangeShift</td>
   * <td>2 x segCount - searchRange</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>endCount[segCount]</td>
   * <td>End characterCode for each segment,<BR>
   * last =0xFFFF.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>reservedPad</td>
   * <td>Set to 0.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>startCount[segCount]</td>
   * <td>Start character code for each segment.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>idDelta[segCount]</td>
   * <td>Delta for all character codes in segment.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>idRangeOffset[segCount]</td>
   * <td>Offsets into glyphIdArray or 0</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>glyphIdArray[ ]</td>
   * <td>Glyph index array (arbitrary length)</td>
   * </tr>
   * </table>
   */
  public class Format4 extends Format {

    /**
     * endCode.
     */
    private final int[] endCode;

    /**
     * entrySelector.
     */
    private final int entrySelector;

    /**
     * glyphIdArray.
     */
    private final int[] glyphIdArray;

    /**
     * idDelta.
     */
    private final int[] idDelta;

    /**
     * idRangeOffset.
     */
    private final int[] idRangeOffset;

    /**
     * language.
     */
    private int language;

    /**
     * rangeShift.
     */
    private final int rangeShift;

    /**
     * searchRange.
     */
    private final int searchRange;

    /**
     * segCount.
     */
    private final int segCount;

    /**
     * segCountX2.
     */
    private final int segCountX2;

    /**
     * startCode.
     */
    private final int[] startCode;

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format4( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT4, rar );
      segCountX2 = rar.readUnsignedShort();
      segCount = segCountX2 / 2;
      endCode = new int[ segCount ];
      startCode = new int[ segCount ];
      idDelta = new int[ segCount ];
      idRangeOffset = new int[ segCount ];
      searchRange = rar.readUnsignedShort();
      entrySelector = rar.readUnsignedShort();
      rangeShift = rar.readUnsignedShort();
      for( int i = 0; i < segCount; i++ ) {
        endCode[ i ] = rar.readUnsignedShort();
      }
      rar.readUnsignedShort(); // reservePad
      for( int i = 0; i < segCount; i++ ) {
        startCode[ i ] = rar.readUnsignedShort();
      }
      for( int i = 0; i < segCount; i++ ) {
        idDelta[ i ] = rar.readUnsignedShort();
      }
      for( int i = 0; i < segCount; i++ ) {
        idRangeOffset[ i ] = rar.readUnsignedShort();
      }

      // Whatever remains of this header belongs in glyphIdArray
      int count = (getLength() - 16 - (segCount * 8)) / 2;
      glyphIdArray = new int[ count ];
      for( int i = 0; i < count; i++ ) {
        glyphIdArray[ i ] = rar.readUnsignedShort();
      }
    }

    /**
     * Returns the endCode.
     *
     * @return Returns the endCode.
     */
    public int[] getEndCode() {

      return endCode;
    }

    /**
     * Returns the entrySelector.
     *
     * @return Returns the entrySelector.
     */
    public int getEntrySelector() {

      return entrySelector;
    }

    /**
     * Returns the glyphIdArray.
     *
     * @return Returns the glyphIdArray.
     */
    public int[] getGlyphIdArray() {

      return glyphIdArray;
    }

    /**
     * Returns the idDelta.
     *
     * @return Returns the idDelta.
     */
    public int[] getIdDelta() {

      return idDelta;
    }

    /**
     * Returns the idRangeOffset.
     *
     * @return Returns the idRangeOffset.
     */
    public int[] getIdRangeOffset() {

      return idRangeOffset;
    }

    /**
     * Returns the language.
     *
     * @return Returns the language.
     */
    public int getLanguage() {

      return language;
    }

    /**
     * Returns the rangeShift.
     *
     * @return Returns the rangeShift.
     */
    public int getRangeShift() {

      return rangeShift;
    }

    /**
     * Returns the searchRange.
     *
     * @return Returns the searchRange.
     */
    public int getSearchRange() {

      return searchRange;
    }

    /**
     * Returns the segCount.
     *
     * @return Returns the segCount.
     */
    public int getSegCount() {

      return segCount;
    }

    /**
     * Returns the segCountX2.
     *
     * @return Returns the segCountX2.
     */
    public int getSegCountX2() {

      return segCountX2;
    }

    /**
     * Returns the startCode.
     *
     * @return Returns the startCode.
     */
    public int[] getStartCode() {

      return startCode;
    }

    @Override
    public int mapCharCode( int charCode ) {

      try {
        for( int i = 0; i < segCount; i++ ) {
          if( endCode[ i ] >= charCode ) {
            if( startCode[ i ] <= charCode ) {
              if( idRangeOffset[ i ] > 0 ) {
                return glyphIdArray[ idRangeOffset[ i ] / 2
                    + (charCode - startCode[ i ])
                    - (segCount - i) ];
              }
              return (idDelta[ i ] + charCode) % 65536;

            }
            break;
          }
        }
      } catch( ArrayIndexOutOfBoundsException e ) {
        return -1;
      }
      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );
      writer.writeAttribute( "segCountX2", segCountX2 );
      writer.writeAttribute( "searchRange", searchRange );
      writer.writeAttribute( "entryselector", entrySelector );
      writer.writeAttribute( "rangeshift", rangeShift );
      writer.writeStartElement( "endcode" );
      writer.writeIntArrayAsEntries( endCode );
      writer.writeEndElement();
      writer.writeStartElement( "startcode" );
      writer.writeIntArrayAsEntries( startCode );
      writer.writeEndElement();
      writer.writeStartElement( "idDelta" );
      writer.writeIntArrayAsEntries( idDelta );
      writer.writeEndElement();
      writer.writeStartElement( "idRangeOffset" );
      writer.writeIntArrayAsEntries( idRangeOffset );
      writer.writeEndElement();

      writer.writeStartElement( "mapcharcode" );
      for( int i = 0; i < 0xffff; i++ ) {
        int c = mapCharCode( i );
        if( c > 0 ) {
          writer.writeStartElement( "entry" );
          writer.writeAttribute( "charcode", i );
          writer.writeAttribute( "value", c );
          writer.writeEndElement();
        }
      }
      writer.writeEndElement();

      writer.writeEndElement();
    }

  }

  // -------------------------------------------------------
  // -------------------------------------------------------

  /**
   * Format 6 is used to map 16-bit, 2-byte, characters to glyph indexes. It
   * is sometimes called the trimmed table mapping. It should be used when
   * character codes for a font fall into a single contiguous range. This
   * results in what is termed adense mapping. Two-byte fonts that are not
   * densely mapped (due to their multiple contiguous ranges) should use
   * Format 4.
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>format</td>
   * <td>Format number is set to 6.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>length</td>
   * <td>Length in bytes.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>version</td>
   * <td>Version number (starts at 0)</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>firstCode</td>
   * <td>First character code of subrange.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>entryCount</td>
   * <td>Number of character codes in subrange.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>glyphIdArray [entryCount]</td>
   * <td>Array of glyph index values for character codes in the range.</td>
   * <tr>
   * </table>
   */
  public class Format6 extends Format {

    // private short format;

    // private short length;

    // private short version;

    // private short firstCode;

    // private short entryCount;

    // private short[] glyphIdArray;

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format6( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT6, rar );

    }

    @Override
    public int mapCharCode( int charCode ) {

      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );

      writer.writeEndElement();
    }

  }

  /**
   * Format 8.0 is a bit like format 2, in that it provides for mixed-length
   * character codes. If a font contains Unicode surrogates, it's likely that
   * it will also include other, regular 16-bit Unicodes as well. This
   * requires a format to map a mixture of 16-bit and 32-bit character codes,
   * just as format 2 allows a mixture of 8-bit and 16-bit codes. A
   * simplifying assumption is made: namely, that there are no 32-bit
   * character codes which share the same first 16 bits as any 16-bit
   * character code. This means that the determination as to whether a
   * particular 16-bit value is a standalone character code or the start of a
   * 32-bit character code can be made by looking at the 16-bit value
   * directly, with no further information required.
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td>Type</td>
   * <td>Name</td>
   * <td>Description</td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>Fixed32</td>
   * <td>format</td>
   * <td>Subtable format; set to 8.0</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>length</td>
   * <td>Byte length of this subtable (including the header)</td>
   * </tr>
   * <tr>
   * <td>UInt32</td>
   * <td>language</td>
   * <td>Language code for this encoding subtable, or zero if
   * language-independent</td>
   * </tr>
   * <tr>
   * <td>UInt8</td>
   * <td>is32[65536]</td>
   * <td>Tightly packed array of bits (8K bytes total) indicating whether the
   * particular 16-bit (index) value is the start of a 32-bit character
   * code</td>
   * <tr>
   * </table>
   */
  public class Format8 extends Format {

    /**
     * Create a new object.
     *
     * @param rar input
     * @throws IOException if an error occured
     */
    Format8( RandomAccessR rar ) throws IOException {

      super( TtfTableCMAP.FORMAT8, rar );

    }

    @Override
    public int mapCharCode( int charCode ) {

      return 0;
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "format" );
      addAttributes( writer );

      writer.writeEndElement();
    }

  }

  /**
   * cmap index entry.
   *
   * <table>
   * <caption>TBD</caption>
   * <tbody>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </tbody>
   * <tr>
   * <td>USHORT</td>
   * <td>Platform ID.</td>
   * </tr>
   * <tr>
   * <td>USHORT</td>
   * <td>Platform-specific encoding ID.</td>
   * </tr>
   * <tr>
   * <td>ULONG</td>
   * <td>Byte offset from beginning of table to the subtable for this
   * encoding.</td>
   * </tr>
   * </table>
   */
  public class IndexEntry implements XMLWriterConvertible {

    /**
     * big5.
     */
    private static final int ENC_BIG5 = 3;

    /**
     * johab.
     */
    private static final int ENC_JOHAB = 6;

    /**
     * prc.
     */
    private static final int ENC_PRC = 4;

    /**
     * shiftjis.
     */
    private static final int ENC_SHIFTJIS = 2;

    /**
     * symbol.
     */
    private static final int ENC_SYMBOL = 0;

    /**
     * unicode.
     */
    private static final int ENC_UNICODE = 1;

    /**
     * wansung.
     */
    private static final int ENC_WANSUNG = 5;

    /**
     * encoding id.
     */
    private final int encodingId;

    /**
     * offset.
     */
    private final int offset;

    /**
     * platform id.
     */
    private final int platformId;

    /**
     * Create a new object.
     *
     * @param rar the RandomAccessInput
     * @throws IOException if an error occured
     */
    IndexEntry( RandomAccessR rar ) throws IOException {

      platformId = rar.readUnsignedShort();
      encodingId = rar.readUnsignedShort();
      offset = rar.readInt();
    }

    /**
     * Returns the encoding id.
     *
     * @return Returns the encoding id
     */
    public int getEncodingId() {

      return encodingId;
    }

    /**
     * Returns the encoding name.
     *
     * @return Returns the encoding name.
     */
    public String getEncodingName() {

      if( platformId == PLATFORM_MICROSOFT ) {
        // Windows specific encodings
        switch( encodingId ) {
          case ENC_SYMBOL:
            return " (Symbol)";
          case ENC_UNICODE:
            return " (Unicode)";
          case ENC_SHIFTJIS:
            return " (ShiftJIS)";
          case ENC_BIG5:
            return " (Big5)";
          case ENC_PRC:
            return " (PRC)";
          case ENC_WANSUNG:
            return " (Wansung)";
          case ENC_JOHAB:
            return " (Johab)";
          default:
            return "? " + encodingId + " ?";
        }
      }
      return "? " + encodingId + " ?";
    }

    /**
     * Returns the offset.
     *
     * @return returns the offset
     */
    public int getOffset() {

      return offset;
    }

    /**
     * Returns the platform id.
     *
     * @return Returns the platform id
     */
    public int getPlatformId() {

      return platformId;
    }

    /**
     * Returns the platform name.
     *
     * @return Returns the platform name.
     */
    public String getPlatformName() {

      return TtfTableNAME.getPlatformName( platformId );
    }

    /**
     * Returns the info for this class.
     *
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

      String platform;
      String encoding = "";

      switch( platformId ) {
        case PLATFORM_APPLE_UNICODE:
          platform = " (Apple Unicode)";
          break;
        case PLATFORM_MACINTOSH:
          platform = " (Macintosh)";
          break;
        case PLATFORM_ISO:
          platform = " (Iso)";
          break;
        case PLATFORM_MICROSOFT:
          platform = " (Windows)";
          break;
        default:
          platform = "???";
      }
      if( platformId == PLATFORM_MICROSOFT ) {
        // Windows specific encodings
        switch( encodingId ) {
          case ENC_SYMBOL:
            encoding = " (Symbol)";
            break;
          case ENC_UNICODE:
            encoding = " (Unicode)";
            break;
          case ENC_SHIFTJIS:
            encoding = " (ShiftJIS)";
            break;
          case ENC_BIG5:
            encoding = " (Big5)";
            break;
          case ENC_PRC:
            encoding = " (PRC)";
            break;
          case ENC_WANSUNG:
            encoding = " (Wansung)";
            break;
          case ENC_JOHAB:
            encoding = " (Johab)";
            break;
          default:
            encoding = "";
        }
      }

      StringBuilder buf = new StringBuilder( "Cmap index entry\n" );
      buf.append( "   platform id  : " + platformId + "  "
                      + platform + '\n' );
      buf.append( "   encoding id  : " + encodingId + "  "
                      + encoding + '\n' );
      buf.append( "   offset       : " + offset + '\n' );
      return buf.toString();
    }

    @Override
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "indexentry" );
      writer.writeAttribute( "platformid", platformId );
      writer.writeAttribute( "encodingid", encodingId );
      writer.writeAttribute( "offset", offset );
      String platform;
      String encoding = "";

      switch( platformId ) {
        case PLATFORM_APPLE_UNICODE:
          platform = " (Apple Unicode)";
          break;
        case PLATFORM_MACINTOSH:
          platform = " (Macintosh)";
          break;
        case PLATFORM_ISO:
          platform = " (Iso)";
          break;
        case PLATFORM_MICROSOFT:
          platform = " (Windows)";
          break;
        default:
          platform = "???";
      }
      writer.writeAttribute( "platform", platform );

      if( platformId == PLATFORM_MICROSOFT ) {
        // Windows specific encodings
        switch( encodingId ) {
          case ENC_SYMBOL:
            encoding = " (Symbol)";
            break;
          case ENC_UNICODE:
            encoding = " (Unicode)";
            break;
          case ENC_SHIFTJIS:
            encoding = " (ShiftJIS)";
            break;
          case ENC_BIG5:
            encoding = " (Big5)";
            break;
          case ENC_PRC:
            encoding = " (PRC)";
            break;
          case ENC_WANSUNG:
            encoding = " (Wansung)";
            break;
          case ENC_JOHAB:
            encoding = " (Johab)";
            break;
          default:
            encoding = "";
        }
      }
      // TODO incomplete
      writer.writeAttribute( "encoding", encoding );
      writer.writeEndElement();
    }
  }

  /**
   * Macintosh Encoding IDs: ENCODING_ARABI.
   */
  public static final short ENCODING_ARABIC = 4;

  /**
   * Macintosh Encoding IDs: ENCODING_ARMENIAN.
   */
  public static final short ENCODING_ARMENIAN = 24;

  /**
   * Macintosh Encoding IDs: ENCODING_BENGALI.
   */
  public static final short ENCODING_BENGALI = 13;

  /**
   * Macintosh Encoding IDs: ENCODING_BURMES.
   */
  public static final short ENCODING_BURMESE = 19;

  /**
   * Macintosh Encoding IDs: ENCODING_CHINESE.
   */
  public static final short ENCODING_CHINESE = 2;

  /**
   * Macintosh Encoding IDs: ENCODING_DEVANAGARI.
   */
  public static final short ENCODING_DEVANAGARI = 9;

  /**
   * Macintosh Encoding IDs: ENCODING_GEEZ.
   */
  public static final short ENCODING_GEEZ = 28;

  /**
   * Macintosh Encoding IDs: ENCODING_GEORGIAN.
   */
  public static final short ENCODING_GEORGIAN = 23;

  /**
   * Macintosh Encoding IDs: ENCODING_GREE.
   */
  public static final short ENCODING_GREEK = 6;

  /**
   * Macintosh Encoding IDs: ENCODING_GUJARATI.
   */
  public static final short ENCODING_GUJARATI = 11;

  /**
   * Macintosh Encoding IDs: ENCODING_GURMUKHI.
   */
  public static final short ENCODING_GURMUKHI = 10;

  /**
   * Macintosh Encoding IDs: ENCODING_HEBREW.
   */
  public static final short ENCODING_HEBREW = 5;

  /**
   * ISO Encoding ISs: ENCODING_ISO_ASCII.
   */
  public static final short ENCODING_ISO_ASCII = 0;

  /**
   * ISO Encoding ISs: ENCODING_ISO_ISO10646.
   */
  public static final short ENCODING_ISO_ISO10646 = 1;

  /**
   * ISO Encoding ISs: ENCODING_ISO_ISO8859_1.
   */
  public static final short ENCODING_ISO_ISO8859_1 = 2;

  /**
   * Macintosh Encoding IDs: ENCODING_JAPENESE.
   */
  public static final short ENCODING_JAPENESE = 1;

  /**
   * Macintosh Encoding IDs: ENCODING_KANADA.
   */
  public static final short ENCODING_KANADA = 16;

  /**
   * Macintosh Encoding IDs: ENCODING_KHMER.
   */
  public static final short ENCODING_KHMER = 20;

  /**
   * Macintosh Encoding IDs: ENCODING_KOREAN.
   */
  public static final short ENCODING_KOREAN = 3;

  /**
   * Macintosh Encoding IDs: ENCODING_LAOTION.
   */
  public static final short ENCODING_LAOTION = 22;

  /**
   * Macintosh Encoding IDs: ENCODING_MALAYALEM.
   */
  public static final short ENCODING_MALAYALEM = 17;

  /**
   * Macintosh Encoding IDs: ENCODING_MALDIVIAN.
   */
  public static final short ENCODING_MALDIVIAN = 25;

  /**
   * Macintosh Encoding IDs: ENCODING_MONGLIAN.
   */
  public static final short ENCODING_MONGLIAN = 27;

  /**
   * Macintosh Encoding IDs: ENCODING_ORIYA.
   */
  public static final short ENCODING_ORIYA = 12;

  /**
   * Macintosh Encoding IDs: ENCODING_R_SYMBOL.
   */
  public static final short ENCODING_R_SYMBOL = 8;

  /**
   * Macintosh Encoding IDs: ENCODING_ROMAN.
   */
  public static final short ENCODING_ROMAN = 0;

  /**
   * Macintosh Encoding IDs: ENCODING_RUSSIA.
   */
  public static final short ENCODING_RUSSIAN = 7;

  /**
   * Macintosh Encoding IDs: ENCODING_SINDHI.
   */
  public static final short ENCODING_SINDHI = 31;

  /**
   * Macintosh Encoding IDs: ENCODING_SINHALESE.
   */
  public static final short ENCODING_SINHALESE = 18;

  /**
   * Macintosh Encoding IDs: ENCODING_SLAVIC.
   */
  public static final short ENCODING_SLAVIC = 29;

  // -------------------------------------------------------
  // -------------------------------------------------------

  /**
   * Macintosh Encoding IDs: ENCODING_TAMIL.
   */
  public static final short ENCODING_TAMIL = 14;

  /**
   * Macintosh Encoding IDs: ENCODING_TELUGU.
   */
  public static final short ENCODING_TELUGU = 15;

  /**
   * Macintosh Encoding IDs: ENCODING_THAI.
   */
  public static final short ENCODING_THAI = 21;

  // -------------------------------------------------------
  // -------------------------------------------------------

  /**
   * Macintosh Encoding IDs: ENCODING_TIBETAN.
   */
  public static final short ENCODING_TIBETAN = 26;

  /**
   * Microsoft Encoding IDs: ENCODING_UG.
   */
  public static final short ENCODING_UGL = 1;

  /**
   * Microsoft Encoding IDs: ENCODING_UNDEFINED.
   */
  public static final short ENCODING_UNDEFINED = 0;

  /**
   * Macintosh Encoding IDs: ENCODING_UNITERP.
   */
  public static final short ENCODING_UNITERP = 32;

  /**
   * Macintosh Encoding IDs: ENCODING_VIETNAMESE.
   */
  public static final short ENCODING_VIETNAMESE = 30;

  /**
   * format 0.
   */
  static final int FORMAT0 = 0;

  /**
   * format 10.
   */
  static final int FORMAT10 = 10;

  /**
   * format 12.
   */
  static final int FORMAT12 = 12;

  /**
   * format 2.
   */
  static final int FORMAT2 = 2;

  /**
   * format 4.
   */
  static final int FORMAT4 = 4;

  /**
   * format 6.
   */
  static final int FORMAT6 = 6;

  /**
   * format 8.
   */
  static final int FORMAT8 = 8;

  /**
   * Microsoft Language IDs: LANGUAGE_ENG.
   */
  public static final short LANGUAGE_ENG = 0x0809;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_ARABIC.
   */
  public static final short LANGUAGE_MAC_ARABIC = 12;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_YUGOSLAVIAN.
   */
  public static final short LANGUAGE_MAC_CHINESE = 19;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_DANISH.
   */
  public static final short LANGUAGE_MAC_DANISH = 7;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_DUTCH.
   */
  public static final short LANGUAGE_MAC_DUTCH = 4;

  // ---------------------------------------------
  // ---------------------------------------------
  /**
   * Macintosh Language IDs: LANGUAGE_MAC_ENGLISH.
   */
  public static final short LANGUAGE_MAC_ENGLISH = 0;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_FINNISH.
   */
  public static final short LANGUAGE_MAC_FINNISH = 13;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_FRENCH.
   */
  public static final short LANGUAGE_MAC_FRENCH = 1;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_GERMAN.
   */
  public static final short LANGUAGE_MAC_GERMAN = 2;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_GREEK.
   */
  public static final short LANGUAGE_MAC_GREEK = 14;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_NORWEGIAN.
   */
  public static final short LANGUAGE_MAC_HEBREW = 10;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_YUGOSLAVIAN.
   */
  public static final short LANGUAGE_MAC_HINDI = 21;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_ICELANDIC.
   */
  public static final short LANGUAGE_MAC_ICELANDIC = 15;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_ITALIAN.
   */
  public static final short LANGUAGE_MAC_ITALIAN = 3;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_JAPANESE.
   */
  public static final short LANGUAGE_MAC_JAPANESE = 11;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_ICELANDIC.
   */
  public static final short LANGUAGE_MAC_MATESE = 16;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_NORWEGIAN.
   */
  public static final short LANGUAGE_MAC_NORWEGIAN = 9;

  /**
   * Macintosh Language IDs: languagePortuguese.
   */
  public static final short LANGUAGE_MAC_PORTUGUESE = 8;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_SPANISH.
   */
  public static final short LANGUAGE_MAC_SPANISH = 6;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_DUTCH.
   */
  public static final short LANGUAGE_MAC_SWEDISH = 5;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_THAI.
   */
  public static final short LANGUAGE_MAC_THAI = 22;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_YUGOSLAVIAN.
   */
  public static final short LANGUAGE_MAC_URDU = 20;

  /**
   * Macintosh Language IDs: LANGUAGE_MAC_YUGOSLAVIAN.
   */
  public static final short LANGUAGE_MAC_YUGOSLAVIAN = 18;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SQI.
   */
  public static final short LANGUAGE_MS_BEL = 0x0423;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_BGR.
   */
  public static final short LANGUAGE_MS_BGR = 0x0402;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_CAT.
   */
  public static final short LANGUAGE_MS_CAT = 0x0403;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_CSY.
   */
  public static final short LANGUAGE_MS_CSY = 0x0405;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DAN.
   */
  public static final short LANGUAGE_MS_DAN = 0x0406;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DEA.
   */
  public static final short LANGUAGE_MS_DEA = 0x0c07;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DEC.
   */
  public static final short LANGUAGE_MS_DEC = 0x1407;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DEL.
   */
  public static final short LANGUAGE_MS_DEL = 0x1007;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DES.
   */
  public static final short LANGUAGE_MS_DES = 0x0807;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRL.
   */
  public static final short LANGUAGE_MS_DEU = 0x0407;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_DEC.
   */
  public static final short LANGUAGE_MS_ELL = 0x0408;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ENA.
   */
  public static final short LANGUAGE_MS_ENA = 0x0c09;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ENC.
   */
  public static final short LANGUAGE_MS_ENC = 0x1009;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ENI.
   */
  public static final short LANGUAGE_MS_ENI = 0x1809;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ENU.
   */
  public static final short LANGUAGE_MS_ENU = 0x0409;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ENC.
   */
  public static final short LANGUAGE_MS_ENZ = 0x1409;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ESM.
   */
  public static final short LANGUAGE_MS_ESM = 0x080a;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ESN.
   */
  public static final short LANGUAGE_MS_ESN = 0x0c0a;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ESP.
   */
  public static final short LANGUAGE_MS_ESP = 0x040a;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ETI.
   */
  public static final short LANGUAGE_MS_ETI = 0x0425;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_EUQ.
   */
  public static final short LANGUAGE_MS_EUQ = 0x042d;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ETI.
   */
  public static final short LANGUAGE_MS_FIN = 0x040b;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRA.
   */
  public static final short LANGUAGE_MS_FRA = 0x040c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRB.
   */
  public static final short LANGUAGE_MS_FRB = 0x080c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRC.
   */
  public static final short LANGUAGE_MS_FRC = 0x0c0c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRL.
   */
  public static final short LANGUAGE_MS_FRL = 0x140c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_FRS.
   */
  public static final short LANGUAGE_MS_FRS = 0x100c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_HUN.
   */
  public static final short LANGUAGE_MS_HUN = 0x040e;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ISL.
   */
  public static final short LANGUAGE_MS_ISL = 0x040f;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ISL.
   */
  public static final short LANGUAGE_MS_ITA = 0x0410;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_ITS.
   */
  public static final short LANGUAGE_MS_ITS = 0x0810;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_LTH.
   */
  public static final short LANGUAGE_MS_LTH = 0x0427;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_LVI.
   */
  public static final short LANGUAGE_MS_LVI = 0x0426;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_NLD.
   */
  public static final short LANGUAGE_MS_NLB = 0x0813;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_NLD.
   */
  public static final short LANGUAGE_MS_NLD = 0x0413;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_NOR.
   */
  public static final short LANGUAGE_MS_NON = 0x0814;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_NOR.
   */
  public static final short LANGUAGE_MS_NOR = 0x0414;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_PLK.
   */
  public static final short LANGUAGE_MS_PLK = 0x0415;

  // --------------------------------------
  // --------------------------------------
  // --------------------------------------
  // --------------------------------------

  /**
   * Microsoft Language IDs: LANGUAGE_MS_PTB.
   */
  public static final short LANGUAGE_MS_PTB = 0x0416;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_PTG.
   */
  public static final short LANGUAGE_MS_PTG = 0x0816;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_PTG.
   */
  public static final short LANGUAGE_MS_ROM = 0x0418;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_RUS.
   */
  public static final short LANGUAGE_MS_RUS = 0x0419;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SHL.
   */
  public static final short LANGUAGE_MS_SHL = 0x041a;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SKY.
   */
  public static final short LANGUAGE_MS_SKY = 0x041b;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SLV.
   */
  public static final short LANGUAGE_MS_SLV = 0x0424;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SQI.
   */
  public static final short LANGUAGE_MS_SQI = 0x041c;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_SVE.
   */
  public static final short LANGUAGE_MS_SVE = 0x041d;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_TRK.
   */
  public static final short LANGUAGE_MS_TRK = 0x041f;

  /**
   * Microsoft Language IDs: LANGUAGE_MS_UKR.
   */
  public static final short LANGUAGE_MS_UKR = 0x0422;

  /**
   * Macintosh Language IDs: LANGUAGE_TURKISH.
   */
  public static final short LANGUAGE_TURKISH = 17;

  // -------------------------------------------------------
  // -------------------------------------------------------
  /**
   * Platform ID: PLATFORMAPPLEUNICODE.
   */
  public static final short PLATFORM_APPLE_UNICODE = 0;

  /**
   * Platform ID: PLATFORM_ISO.
   */
  public static final short PLATFORM_ISO = 2;

  /**
   * Platform ID: PLATFORM_MACINTOSH.
   */
  public static final short PLATFORM_MACINTOSH = 1;

  /**
   * Platform ID: PLATFORM_MICROSOFT.
   */
  public static final short PLATFORM_MICROSOFT = 3;

  /**
   * index entries.
   */
  private final IndexEntry[] entries;

  /**
   * formats.
   */
  private final Format[] formats;

  /**
   * number of tables.
   */
  private final int numTables;

  /**
   * Version.
   */
  private final int version;

  // --------------------------------------------------
  // --------------------------------------------------
  // --------------------------------------------------
  // --------------------------------------------------

  /**
   * Create a new object.
   *
   * @param tablemap the table map
   * @param de       directory entry
   * @param rar      the RandomAccessInput
   * @throws IOException if an error occurred
   */
  public TtfTableCMAP( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                       RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );

    long fp = rar.getPointer();
    version = rar.readUnsignedShort();
    numTables = rar.readUnsignedShort();
    entries = new IndexEntry[ numTables ];
    formats = new Format[ numTables ];

    // Get each of the index entries
    for( int i = 0; i < numTables; i++ ) {
      entries[ i ] = new IndexEntry( rar );
    }

    // Get each of the tables
    for( int i = 0; i < numTables; i++ ) {
      rar.seek( fp + entries[ i ].getOffset() );
      int format = rar.readUnsignedShort();
      Format cmapformat = null;
      switch( format ) {
        case FORMAT0:
          cmapformat = new Format0( rar );
          break;
        case FORMAT2:
          cmapformat = new Format2( rar );
          break;
        case FORMAT4:
          cmapformat = new Format4( rar );
          break;
        case FORMAT6:
          cmapformat = new Format6( rar );
          break;
        case FORMAT8:
          break;
        case FORMAT10:
          break;
        case FORMAT12:
          break;
        default:
          // wrong format - ignore
          // null;
      }

      formats[ i ] = cmapformat;
    }
  }

  // -------------------------------------------
  // -------------------------------------------
  // -------------------------------------------
  // -------------------------------------------
  // -------------------------------------------

  /**
   * Returns the entries.
   *
   * @return Returns the entries.
   */
  public IndexEntry[] getEntries() {

    return entries;
  }

  /**
   * Returns the cmap format.
   *
   * @param platformId platform id
   * @param encodingId encoding id
   * @return Returns the cmap format
   */
  public Format getFormat( short platformId, short encodingId ) {

    // Find the requested format
    for( int i = 0; i < numTables; i++ ) {
      if( entries[ i ].getPlatformId() == platformId
          && entries[ i ].getEncodingId() == encodingId ) {
        return formats[ i ];
      }
    }
    return null;
  }

  /**
   * Returns the formats.
   *
   * @return Returns the formats.
   */
  public Format[] getFormats() {

    return formats;
  }

  /**
   * Returns the numTables.
   *
   * @return Returns the numTables.
   */
  public int getNumTables() {

    return numTables;
  }

  @Override
  public String getShortcut() {

    return "cmap";
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  @Override
  public int getType() {

    return XtfReader.CMAP;
  }

  /**
   * Returns the version.
   *
   * @return Returns the version.
   */
  public int getVersion() {

    return version;
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeAttribute( "version",
                           String.valueOf( XtfReader.convertVersion( version ) ) );
    writer.writeAttribute( "numberoftables", String.valueOf( numTables ) );

    for( int i = 0; i < entries.length; i++ ) {
      IndexEntry ie = entries[ i ];
      if( ie != null ) {
        entries[ i ].writeXML( writer );
      }
    }
    for( int i = 0; i < formats.length; i++ ) {
      Format f = formats[ i ];
      if( f != null ) {
        formats[ i ].writeXML( writer );
      }
    }
    writer.writeEndElement();
  }

}
