/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.afm;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.*;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.FontException;
import org.extex.font.fontparameter.FontParameter;
import org.extex.font.format.AfmMetricFont;
import org.extex.font.format.pfb.PfbParser;
import org.extex.font.unicode.GlyphName;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class to load afm fonts.
 *
 * <ul>
 * <li>The font has no design size; this is set to the size of the font key
 * .</li>
 * <li>The EM size is set to the size of the font.</li>
 * <li>The font load the font dimen values over {@code FontParameter}.
 * <li>If the font has no glyph 'space', then ex is used for getSpace().</li>
 * </ul>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class LoadableAfmFont
    implements
    LoadableFont,
    ResourceAware,
    BackendFont,
    LogEnabled,
    AfmMetricFont {

  /**
   * The actual dynamic encoding vector.
   */
  private String[] actualDynEnc;

  /**
   * The actual position in the encoding vector.
   */
  private int actualDynPos;

  /**
   * The actual font key.
   */
  private FontKey actualFontKey;

  /**
   * The list of encoding vectors.
   */
  private List<String[]> encodingVectors;

  /**
   * The position of the encoding vector.
   */
  private int encodingVectorsPos;

  /**
   * The resource finder.
   */
  private ResourceFinder finder;

  /**
   * The map for the font dimen values.
   */
  private final Map<String, FixedDimen> fontDimen =
      new HashMap<String, FixedDimen>();

  /**
   * The font key.
   */
  private FontKey fontKey;

  /**
   * The glyph name Unicode table.
   */
  private GlyphName glyphname;

  /**
   * Has multi fonts.
   */
  private boolean hasMultiFonts;

  /**
   * The last used char metric (for caching).
   */
  private AfmCharMetric lastUsedCm;

  /**
   * The last used Unicode char (for caching).
   */
  private UnicodeChar lastUsedUc;

  /**
   * The field {@code localizer} contains the localizer. It is initiated with
   * a localizer for the name of this class.
   */
  private final Localizer localizer = LocalizerFactory
      .getLocalizer( LoadableAfmFont.class );

  /**
   * The logger.
   */
  private Logger logger;

  /**
   * Use no kerning information.
   */
  private boolean nokerning;

  /**
   * Use no ligature information.
   */
  private boolean noligature;

  /**
   * The font parameter
   */
  private FontParameter param;

  /**
   * The afm parser.
   */
  private AfmParser parser;

  /**
   * The pfa data.
   */
  private byte[] pfadata;

  /**
   * The pfb data.
   */
  private byte[] pfbdata;

  /**
   * Map for the used code points and the corresponding encoding vector
   * position.
   */
  private final Map<Integer, Integer> useCodepoints =
      new HashMap<Integer, Integer>();


  @Override
  public void enableLogging( Logger logger ) {

    this.logger = logger;
  }

  /**
   * Convert a float value to a {@code Dimen}.
   *
   * @param val the value
   * @return the {@code Dimen} value of the float value.
   */
  private FixedDimen floatToDimen( float val ) {

    long l = (long) (getActualSize().getValue() * val / 1000);

    return new Dimen( l );
  }


  @Override
  public FontKey getActualFontKey() {

    return actualFontKey;
  }


  @Override
  public FixedDimen getActualSize() {

    return actualFontKey.getDimen( "size" );
  }


  public byte[] getAfm() {

    return parser.getFontData();
  }

  /**
   * Returns the char metric for a Unicode char, or {@code null}, if not
   * found.
   *
   * @param uc the Unicode char.
   * @return the char metric for a Unicode char, or {@code null}, if not
   * found.
   */
  private AfmCharMetric getCharMetric( UnicodeChar uc ) {

    if( uc != null ) {
      if( uc == lastUsedUc ) {
        return lastUsedCm;
      }
      // get thy glyph name
      String name = null;
      if( param != null ) {
        name = param.getGlyphname( uc );
      }
      if( name == null ) {
        name = glyphname.getGlyphname( uc );
      }
      if( name != null ) {
        AfmCharMetric cm = parser.getAfmCharMetric( name );
        if( cm != null ) {
          lastUsedUc = uc;
          lastUsedCm = cm;
          return cm;
        }
      }
    }
    return null;
  }


  public int getCheckSum() {

    return 0;
  }


  @Override
  public FixedGlue getDepth( UnicodeChar uc ) {

    AfmCharMetric cm = getCharMetric( uc );
    if( cm != null ) {
      return new Glue( floatToDimen( cm.getDepth() ) );
    }
    return FixedGlue.ZERO;
  }


  @Override
  public FixedDimen getDesignSize() {

    return fontKey.getDimen( "size" );
  }


  @Override
  public FixedDimen getEm() {

    return actualFontKey.getDimen( "size" );
  }


  public int getEncodingForChar( int codepoint ) {

    Integer pos = useCodepoints.get( codepoint );
    if( pos != null ) {
      return pos;
    }

    return -1;
  }


  public List<String[]> getEncodingVectors() {

    return encodingVectors;
  }


  @Override
  public FixedDimen getEx() {

    FixedDimen val = fontDimen.get( "XHEIGHT" );
    if( val != null ) {
      return val;
    }
    float xh = parser.getHeader().getXheight();
    return floatToDimen( xh );
  }


  @Override
  public FixedDimen getFontDimen( String name ) {

    FixedDimen val = fontDimen.get( name );
    if( val != null ) {
      return val;
    }
    return Dimen.ZERO_PT;
  }


  @Override
  public FontKey getFontKey() {

    return fontKey;
  }


  @Override
  public String getFontName() {

    return fontKey.getName();
  }


  @Override
  public FixedGlue getHeight( UnicodeChar uc ) {

    AfmCharMetric cm = getCharMetric( uc );
    if( cm != null ) {
      return new Glue( floatToDimen( cm.getHeight() ) );
    }
    return FixedGlue.ZERO;
  }


  @Override
  public FixedDimen getItalicCorrection( UnicodeChar uc ) {

    // TODO mgn: getItalicCorrection unimplemented
    return null;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  @Override
  public FixedDimen getKerning( UnicodeChar uc1, UnicodeChar uc2 ) {

    if( nokerning ) {
      return Dimen.ZERO_PT;
    }
    AfmCharMetric cm1 = getCharMetric( uc1 );

    if( cm1 != null ) {
      AfmCharMetric cm2 = getCharMetric( uc2 );
      if( cm2 != null ) {
        AfmKernPairs kp = cm1.getAfmKernPair( cm2.getN() );
        if( kp != null ) {
          float size = kp.getKerningsize();
          return floatToDimen( size );
        }
      }
    }
    return Dimen.ZERO_PT;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  @Override
  public UnicodeChar getLigature( UnicodeChar uc1, UnicodeChar uc2 ) {

    if( noligature ) {
      return null;
    }
    AfmCharMetric cm1 = getCharMetric( uc1 );

    if( cm1 != null ) {
      AfmCharMetric cm2 = getCharMetric( uc2 );
      if( cm2 != null ) {
        String lig = cm1.getLigature( cm2.getN() );
        if( lig != null ) {
          try {
            UnicodeChar uc = null;
            if( param != null ) {
              uc = param.getUnicode( lig );
            }
            if( uc == null ) {
              uc = GlyphName.getInstance().getUnicode( lig );
            }
            return uc;
          } catch( IOException e ) {
            return null;
          }
        }
      }
    }
    return null;
  }


  @Override
  public String getName() {

    return actualFontKey.getName();
  }


  @Override
  public byte[] getPfa() {

    return pfadata;
  }


  @Override
  public byte[] getPfb() {

    if( pfbdata == null ) {
      // search pfb file
      InputStream pfbin =
          finder.findResource( actualFontKey.getName(), "pfb" );
      if( pfbin == null ) {
        logger.severe( localizer.format( "Afm.missingPfb",
                                         actualFontKey.getName() ) );
      }
      else {
        try {
          PfbParser pfbParser = new PfbParser( pfbin );
          pfbdata = pfbParser.getPfbdata();
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          pfbParser.toPfa( out );
          pfadata = out.toByteArray();
        } catch( Exception e ) {
          logger.severe( e.getLocalizedMessage() );
        }
      }
    }
    return pfbdata;
  }


  @Override
  public FixedCount getScaleFactor() {

    return Count.ONE;
  }


  @Override
  public FixedGlue getSpace() {

    FixedDimen val = fontDimen.get( "SPACE" );
    if( val != null ) {
      return new Glue( val );
    }
    AfmCharMetric space = parser.getAfmCharMetric( "space" );
    if( space != null ) {
      float wx = space.getWx();
      return new Glue( floatToDimen( wx ) );
    }
    return new Glue( getEx() );
  }


  @Override
  public FixedGlue getWidth( UnicodeChar uc ) {

    AfmCharMetric cm = getCharMetric( uc );
    if( cm != null ) {
      return new Glue( floatToDimen( cm.getWidth() ) );
    }
    return FixedGlue.ZERO;
  }


  @Override
  public byte[] getXtf() {

    return null;
  }

  public boolean hasEncodingVector() {

    // TODO mgn: hasEncodingVector unimplemented
    return false;
  }

  @Override
  public boolean hasGlyph( UnicodeChar uc ) {

    return getCharMetric( uc ) != null;
  }

  public boolean hasMultiFonts() {

    return hasMultiFonts;
  }

  @Override
  public boolean isType1() {

    return true;
  }

  @Override
  public boolean isXtf() {

    return false;
  }

  @Override
  public void loadFont( InputStream in, CoreFontFactory factory, FontKey key )
      throws CorruptFontException {

    this.fontKey = key;

    if( key == null ) {
      throw new IllegalArgumentException( "fontkey" );
    }

    try {

      glyphname = GlyphName.getInstance();

      parser = new AfmParser( in );

      InputStream paramin =
          finder.findResource( fontKey.getName(), "fontinfo" );
      if( paramin != null ) {
        param = new FontParameter( paramin );
      }

    } catch( FontException | IOException e ) {
      throw new CorruptFontException( key, e.getLocalizedMessage() );
    }

    if( key.getDimen( "size" ) == null ) {
      // use 10pt as default
      actualFontKey = factory.getFontKey( key, new Dimen( Dimen.ONE * 10 ) );
    }
    else {
      actualFontKey = key;
    }

    setFontDimenValues();

    // noligature, nokerning
    if( actualFontKey.hasBoolean( FontKey.KERNING ) ) {
      boolean b = actualFontKey.getBoolean( FontKey.KERNING );
      nokerning = !b;
    }
    if( actualFontKey.hasBoolean( FontKey.LIGATURES ) ) {
      boolean b = actualFontKey.getBoolean( FontKey.LIGATURES );
      noligature = !b;
    }
  }

  /**
   * Set the font dimen values.
   */
  private void setFontDimenValues() {

    if( param != null ) {
      Map<String, Integer> fd = param.getFontDimen();
      for( String key : fd.keySet() ) {
        Integer val = fd.get( key );
        if( val != null ) {
          fontDimen.put( key, floatToDimen( val ) );
        }
      }
    }
  }

  @Override
  public void setResourceFinder( ResourceFinder finder ) {

    this.finder = finder;
  }

  public void usedCharacter( BackendCharacter bc ) {

    // check, if the char is in the default encoding.
    int codepoint = bc.getId();
    if( codepoint >= 0 && codepoint < 256 ) {
      String glyph = parser.getDefaultEncodingVector()[ codepoint ];
      if( glyph != null && !glyph.equals( ".notdef" ) ) {
        // default encoding -> exit
        return;
      }
    }

    if( actualDynEnc == null ) {
      actualDynEnc = new String[ 256 ];
      Arrays.fill( actualDynEnc, ".notdef" );
      if( encodingVectors == null ) {
        encodingVectors = new ArrayList<String[]>();
      }
      encodingVectors.add( actualDynEnc );
      encodingVectorsPos = encodingVectors.size() - 1;
      if( encodingVectors.size() >= 1 ) {
        hasMultiFonts = true;
      }
    }
    if( !useCodepoints.containsKey( codepoint ) ) {
      useCodepoints.put( codepoint, encodingVectorsPos );

      try {
        String name =
            GlyphName.getInstance().getGlyphname(
                UnicodeChar.get( codepoint ) );
        actualDynEnc[ actualDynPos++ ] = name;
        if( actualDynPos >= actualDynEnc.length ) {
          actualDynEnc = null;
        }

      } catch( IOException e ) {
        logger.severe( localizer.format( "Afm.errorGlyphName",
                                         e.getLocalizedMessage() ) );
      }
    }
  }

}
