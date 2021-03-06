/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.tfm;

import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class for TFM header information.
 *
 * <p>
 * header : array [0 .. (lh-1)] of stuff
 * </p>
 *
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td>header[0]</td>
 * <td>a 32-bit check sum</td>
 * </tr>
 * <tr>
 * <td>header[1]</td>
 * <td>a fix word containing the design size of the font, in units of TEX points
 * (7227 TEX points =254 cm)</td>
 * </tr>
 * <tr>
 * <td>header[2..11]</td>
 * <td>if present, contains 40 bytes that identify the character coding
 * scheme.</td>
 * </tr>
 * <tr>
 * <td>header[12..16]</td>
 * <td>if present, contains 20 bytes that name the font family.</td>
 * </tr>
 * <tr>
 * <td>header[17]</td>
 * <td>if present, contains a first byte called the seven_bit_safe_flag, then
 * two bytes that are ignored, and a fourth byte called the face.
 * <tr>
 * <td>header[18..(lh-1)]</td>
 * <td>might also be present: the individual words are simply called header[18],
 * header[19], etc., at the moment.</td>
 * </tr>
 * </table>
 *
 * <p>
 * Information from: The DVI Driver Standard, Level 0 The TUG DVI Driver
 * Standards Committee
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TfmHeaderArray implements Serializable {

  /**
   * Size of coding scheme header information in words
   */
  private static final int CODING_SCHEME_SIZE = 10;

  /**
   * Size of font family header information in words
   */
  private static final int FONT_FAMILY_SIZE = 5;

  /**
   * header[18..]
   */
  public static final int HEADER_REST_SIZE = 18;

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * header[0]: checksum
   */
  private final int checksum;

  /**
   * header[2..11]: coding scheme
   */
  private String codingscheme;

  /**
   * header[1]: design size
   */
  private final TfmFixWord designsize;

  /**
   * header[12..16]: font family
   */
  private String fontfamily;

  /**
   * the font type (VANILLA, MATHSY, MATHEX)
   */
  private TfmFontType fonttype;

  /**
   * Uninterpreted rest of the header
   */
  private int[] headerrest = null;

  /**
   * True if only 7 bit character codes are used.
   */
  private boolean sevenBitSafe = false;

  /**
   * BaseFont Xerox face code
   */
  private int xeroxfacecode = -1;

  /**
   * Create a new object
   *
   * @param rar the input
   * @param lh  length of the header data
   * @throws IOException if an IO-error occurs.
   */
  public TfmHeaderArray( RandomAccessR rar, int lh ) throws IOException {

    int hr = lh;
    checksum = rar.readInt();
    hr--;
    designsize =
        new TfmFixWord( rar.readInt(), TfmFixWord.FIXWORDDENOMINATOR );
    hr--;

    // optional: coding scheme
    if( hr >= CODING_SCHEME_SIZE ) {
      codingscheme =
          readBCPL( rar, TfmConstants.CONST_4 * CODING_SCHEME_SIZE );
      fonttype = new TfmFontType( codingscheme );
      hr -= CODING_SCHEME_SIZE;

      // optional: font family
      if( hr >= FONT_FAMILY_SIZE ) {
        fontfamily =
            readBCPL( rar, TfmConstants.CONST_4 * FONT_FAMILY_SIZE );
        hr -= FONT_FAMILY_SIZE;
        // optional: seven_bit_safe_flag
        if( hr >= 1 ) {
          sevenBitSafe = (rar.readByte() > TfmConstants.CONST_127);
          // ignore 2 bytes
          rar.skipBytes( 2 );
          // face
          xeroxfacecode = rar.readByteAsInt();
          hr--;
          // optional rest
          if( hr > 0 ) {
            headerrest = new int[ hr ];
            for( int i = 0; i < hr; i++ ) {
              headerrest[ i ] = rar.readInt();
            }
          }
        }
      }
    }
  }

  /**
   * Returns the checksum.
   *
   * @return Returns the checksum.
   */
  public int getChecksum() {

    return checksum;
  }

  /**
   * Returns the codingscheme.
   *
   * @return Returns the codingscheme.
   */
  public String getCodingscheme() {

    return codingscheme;
  }

  /**
   * Returns the designsize.
   *
   * @return Returns the designsize.
   */
  public TfmFixWord getDesignsize() {

    return designsize;
  }

  /**
   * Returns the face.
   *
   * @return Returns the face.
   */
  public int getFace() {

    return xeroxfacecode;
  }

  /**
   * Returns the fontfamily.
   *
   * @return Returns the fontfamily.
   */
  public String getFontfamily() {

    return fontfamily;
  }

  /**
   * Returns the fontype.
   *
   * @return Returns the fontype.
   */
  public TfmFontType getFontType() {

    return fonttype;
  }

  /**
   * Returns the headerrest.
   *
   * @return Returns the headerrest.
   */
  public int[] getHeaderrest() {

    return headerrest;
  }

  /**
   * Getter for xeroxfacecode.
   *
   * @return Returns the xeroxfacecode.
   */
  public int getXeroxfacecode() {

    return xeroxfacecode;
  }

  /**
   * Returns the sevenBitSafe.
   *
   * @return Returns the sevenBitSafe.
   */
  public boolean isSevenBitSafe() {

    return sevenBitSafe;
  }

  /**
   * Reads a character string from the header. The string is stored as its
   * length in first byte then the string (the rest of area is not used).
   *
   * @param rar  the input
   * @param size the size of string area in the header.
   * @return the string
   * @throws IOException if an I/O error occurred.
   */
  private String readBCPL( RandomAccessR rar, int size ) throws IOException {

    int len = rar.readByte();
    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < size - 1; i++ ) {
      char c = Character.toUpperCase( (char) rar.readByte() );
      if( i < len ) {
        buf.append( c );
      }
    }
    return buf.toString();
  }

}
