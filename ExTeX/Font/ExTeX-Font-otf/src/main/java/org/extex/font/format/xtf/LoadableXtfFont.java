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

package org.extex.font.format.xtf;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.*;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.format.XtfMetricFont;
import org.extex.font.format.xtf.tables.XtfBoundingBox;
import org.extex.font.unicode.GlyphName;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class to load xtf fonts.
 *
 * <p>
 * At the moment only ttf!
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
@SuppressWarnings("unused")
public class LoadableXtfFont
    implements
    LoadableFont,
    XtfMetricFont,
    BackendFont,
    LogEnabled {

  /**
   * The actual font key.
   */
  private FontKey actualFontKey;

  /**
   * The font key.
   */
  private FontKey fontKey;

  /**
   * use the first font! TODO MGN incomplete
   */
  private final int fontnumber = 0;

  /**
   * The glyph name Unicode table.
   */
  private GlyphName glyphname;

  /**
   * The logger (it can be {@code null}!).
   */
  private Logger logger;

  /**
   * The xtf reader.
   */
  private XtfReader reader;

  public void enableLogging( Logger logger ) {

    this.logger = logger;

  }

  public FontKey getActualFontKey() {

    return actualFontKey;
  }

  public FixedDimen getActualSize() {

    return actualFontKey.getDimen( "size" );
  }

  public byte[] getAfm() {

    return null;
  }

  public int getCheckSum() {

    // not available in ttf/otf.
    return 0;
  }

  public FixedGlue getDepth( UnicodeChar uc ) {

    int d = 0;

    // TODO encoding
    XtfBoundingBox bb =
        reader.mapCharCodeToBB( uc.getCodePoint(), fontnumber,
                                (short) 3, (short) 1 );
    if( bb != null ) {

      d = bb.getDepth();
    }
    return new Glue( intToDimen( d ) );
  }

  public FixedDimen getDesignSize() {

    return fontKey.getDimen( "size" );
  }

  public FixedDimen getEm() {

    return getActualSize();
  }

  public int getEncodingForChar( int codepoint ) {

    return -1;
  }

  public List<String[]> getEncodingVectors() {

    return null;
  }

  public FixedDimen getEx() {

    int xh = 0;

    // TODO encoding
    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "x", fontnumber, (short) 3, (short) 1 );
    if( bb != null ) {

      xh = bb.getHeight();
    }
    return intToDimen( xh );
  }

  public FixedDimen getFontDimen( String name ) {

    // TODO mgn: getFontDimen unimplemented
    return null;
  }

  public FontKey getFontKey() {

    return fontKey;
  }

  public String getFontName() {

    return fontKey.getName();
  }

  /**
   * Getter for glyphname.
   *
   * @return the glyphname
   */
  public GlyphName getGlyphname() {

    return glyphname;
  }

  public FixedGlue getHeight( UnicodeChar uc ) {

    int h = 0;

    // TODO encoding
    XtfBoundingBox bb =
        reader.mapCharCodeToBB( uc.getCodePoint(), fontnumber,
                                (short) 3, (short) 1 );
    if( bb != null ) {

      h = bb.getHeight();
    }
    return new Glue( intToDimen( h ) );
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns always 0pt.
   *
   * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.core.UnicodeChar)
   */
  public FixedDimen getItalicCorrection( UnicodeChar uc ) {

    return Dimen.ZERO_PT;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public FixedDimen getKerning( UnicodeChar uc1, UnicodeChar uc2 ) {

    // TODO mgn: nokerning beachten

    int size = 0;

    if( uc1 != null && uc2 != null ) {
      size =
          reader.mapCharCodetoKerning( uc1.getCodePoint(), uc2
              .getCodePoint(), fontnumber, (short) 3, (short) 1 );
    }

    return intToDimen( size );
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public UnicodeChar getLigature( UnicodeChar uc1, UnicodeChar uc2 ) {

    // TODO mgn: getLigature unimplemented
    return null;
  }

  public String getName() {

    return getActualFontKey().getName();
  }

  public byte[] getPfa() {

    return null;
  }

  public byte[] getPfb() {

    return null;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Returns allways 0.
   *
   * @see org.extex.font.ExtexFont#getScaleFactor()
   */
  public FixedCount getScaleFactor() {

    return Count.ONE;
  }

  public FixedGlue getSpace() {

    // TODO mgn: encoding!
    int width =
        reader.mapCharCodeToWidth( "space", fontnumber, (short) 3,
                                   (short) 1 );
    if( width > 0 ) {
      return new Glue( intToDimen( width ) );
    }
    return new Glue( getEx() );
  }

  public FixedGlue getWidth( UnicodeChar uc ) {

    int w =
        reader.mapCharCodeToWidth( uc.getCodePoint(), fontnumber,
                                   (short) 3, (short) 1 );
    return new Glue( intToDimen( w ) );
  }

  public byte[] getXtf() {

    return reader.getFontData();
  }

  public boolean hasEncodingVector() {

    return false;
  }

  public boolean hasGlyph( UnicodeChar uc ) {

    String glyphname =
        reader.mapCharCodeToGlyphname( uc.getCodePoint(), fontnumber,
                                       (short) 3, (short) 1 );

    return reader.hasGlyph( glyphname, fontnumber );
  }

  public boolean hasMultiFonts() {

    return false;
  }

  /**
   * Convert a int value to a {@code Dimen}.
   *
   * @param val the value
   * @return the {@code Dimen} value of the float value.
   */
  private FixedDimen intToDimen( int val ) {

    int i = val * 1000 / reader.getUnitsPerEm();
    long l = getActualSize().getValue() * i / 1000;

    return new Dimen( l );
  }

  public boolean isType1() {

    return false;
  }

  public boolean isXtf() {

    return true;
  }

  /**
   * org.extex.font.CoreFontFactory, org.extex.font.FontKey)
   */
  public void loadFont( InputStream in, CoreFontFactory factory, FontKey key )
      throws CorruptFontException,
      ConfigurationException {

    fontKey = key;

    if( key == null ) {
      throw new IllegalArgumentException( "fontkey" );
    }

    try {

      glyphname = GlyphName.getInstance();

      reader = new XtfReader( in, logger );

    } catch( IOException e ) {
      throw new CorruptFontException( key, e.getLocalizedMessage() );
    }

    if( key.getDimen( "size" ) == null ) {
      // use 10pt as default
      actualFontKey = factory.getFontKey( key, new Dimen( Dimen.ONE * 10 ) );
    }
    else {
      actualFontKey = key;
    }

    // TODO mgn check the fontnumber
    // use at the moment the first font in cff

  }

  public void usedCharacter( BackendCharacter bc ) {
  }
}
