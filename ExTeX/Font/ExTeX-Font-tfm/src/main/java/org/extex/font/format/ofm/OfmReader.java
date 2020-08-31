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

package org.extex.font.format.ofm;

import org.extex.font.format.tfm.TfmHeaderArray;
import org.extex.util.file.random.RandomAccessInputStream;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class read a OFM-file (Omega-Font_Metric).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class OfmReader {

  /**
   * The lengths in the file.
   */
  private final OfmHeaderLengths lengths;

  /**
   * The header.
   */
  private final TfmHeaderArray header;

  /**
   * The char info array.
   */
  private final OfmCharInfoArray charinfo;

  /**
   * Create e new object.
   *
   * @param in        The input.
   * @param afontname The font name.
   * @throws IOException if a IO-error occurred.
   */
  public OfmReader( InputStream in, String afontname ) throws IOException {

    this( new RandomAccessInputStream( in ), afontname );
  }

  /**
   * Create e new object.
   *
   * @param rar       The input.
   * @param afontname The font name.
   * @throws IOException if a IO-error occurred.
   */
  public OfmReader( RandomAccessR rar, String afontname ) throws IOException {

    fontname = afontname;

    ofmLevel = rar.readInt();

    if( ofmLevel != 0 ) {
      throw new IOException( "Level not supported" );
    }

    // read the input
    lengths = new OfmHeaderLengths( rar );
    header = new TfmHeaderArray( rar, lengths.getLh() );
    charinfo = new OfmCharInfoArray( rar, lengths.getCc() );
    // width = new TfmWidthArray(rar, lengths.getNw());
    // height = new TfmHeightArray(rar, lengths.getNh());
    // depth = new TfmDepthArray(rar, lengths.getNd());
    // italic = new TfmItalicArray(rar, lengths.getNi());
    // ligkern = new TfmLigKernArray(rar, lengths.getNl());
    // kern = new TfmKernArray(rar, lengths.getNk());
    // exten = new TfmExtenArray(rar, lengths.getNe());
    // param = new TfmParamArray(rar, lengths.getNp(),
    // header.getFontType());

    // close input
    rar.close();

  }

  /**
   * The font name.
   */
  private final String fontname;

  /**
   * Th eofm level.
   */
  private final int ofmLevel;

  /**
   * Getter for fontname.
   *
   * @return the fontname
   */
  public String getFontname() {

    return fontname;
  }

  /**
   * Getter for ofmLevel.
   *
   * @return the ofmLevel
   */
  public int getOfmLevel() {

    return ofmLevel;
  }

  /**
   * Getter for lengths.
   *
   * @return the lengths
   */
  public OfmHeaderLengths getLengths() {

    return lengths;
  }

  /**
   * Getter for header.
   *
   * @return the header
   */
  public TfmHeaderArray getHeader() {

    return header;
  }

  /**
   * Getter for charinfo.
   *
   * @return the charinfo
   */
  public OfmCharInfoArray getCharinfo() {

    return charinfo;
  }

}
