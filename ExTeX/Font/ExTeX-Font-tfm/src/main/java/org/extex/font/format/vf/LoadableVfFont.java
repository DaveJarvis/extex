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

package org.extex.font.format.vf;

import org.extex.core.Unicode;
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
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.tfm.TfmReader;
import org.extex.font.format.tfm.U2tFactory;
import org.extex.font.format.vf.command.VfCommandCharacterPackets;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.CharNodeBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.font.impl.FontImpl;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.file.random.RandomAccessInputArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to load the virtual font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class LoadableVfFont
    implements
    LoadableFont,
    BackendFont,
    ResourceAware,
    LogEnabled,
    CharNodeBuilder,
    FontAware {

  /**
   * The actual font key.
   */
  private FontKey actualFontKey;

  /**
   * The code point map.
   */
  private Map<UnicodeChar, Integer> codepointmap;

  /**
   * The resource finder.
   */
  private ResourceFinder finder;

  /**
   * The font key.
   */
  private FontKey fontKey;

  /**
   * The field {@code localizer} contains the localizer. It is initiated
   * with a localizer for the name of this class.
   */
  private final Localizer localizer =
      LocalizerFactory.getLocalizer( LoadableVfFont.class );

  /**
   * The logger.
   */
  private Logger logger;

  /**
   * The tfm reader.
   */
  private TfmReader reader;

  /**
   * The virtual font.
   */
  private VfFont vfFont;

  /**
   * Returns the char position of a Unicode char.
   * <p>
   * If the Unicode char is not defined then a negative value is returned.
   *
   * @param uc the Unicode char.
   * @return the char position of a Unicode char.
   */
  private int charPos( UnicodeChar uc ) {

    if( uc == null ) {
      return -1;
    }

    int cp = uc.getCodePoint();
    if( cp >= Unicode.OFFSET && cp <= Unicode.OFFSET + 0xff ) {
      cp = cp & 0xff;
    }
    else {
      // font: map code point to char position
      Integer ipos = codepointmap.get( uc );
      if( ipos != null ) {
        cp = ipos.intValue();
      }
      else {
        if( cp < 0 || cp > 255 ) {
          cp = -1;
        }
      }
    }
    return cp;
  }

  public void enableLogging( Logger logger ) {

    this.logger = logger;

  }

  public FontKey getActualFontKey() {

    return actualFontKey;
  }

  public FixedDimen getActualSize() {

    return actualFontKey.getDimen( FontKey.SIZE );
  }

  public byte[] getAfm() {

    // TODO mgn: getAfm unimplemented
    return null;
  }

  public int getCheckSum() {

    return reader.getChecksum();
  }

  public FixedGlue getDepth( UnicodeChar uc ) {

    int cp = charPos( uc );
    if( cp >= 0 && reader.getDepth( cp ) != null ) {
      return new Glue( (getActualSize().getValue() * reader.getDepth( cp )
                                                           .getValue()) >> 20 );
    }
    return FixedGlue.ZERO;
  }

  public FixedDimen getDesignSize() {

    return reader.getDesignSize();
  }

  public FixedDimen getEm() {

    return getFontDimen( "QUAD" );
  }

  public int getEncodingForChar( int codepoint ) {

    // TODO mgn: getEncodingForChar unimplemented
    return 0;
  }

  public List<String[]> getEncodingVectors() {

    // TODO mgn: getEncodingVectors unimplemented
    return null;
  }

  public FixedDimen getEx() {

    return getFontDimen( "XHEIGHT" );
  }

  public FixedDimen getFontDimen( String name ) {

    TfmFixWord param = reader.getParamAsFixWord( name );

    return new Dimen(
        ((getActualSize().getValue() * param.getValue()) >> 20) );
  }

  public FontKey getFontKey() {

    return fontKey;
  }

  public String getFontName() {

    return reader.getFontname();
  }

  public FixedGlue getHeight( UnicodeChar uc ) {

    int cp = charPos( uc );
    if( cp >= 0 && reader.getHeight( cp ) != null ) {
      return new Glue( (getActualSize().getValue() * reader.getHeight( cp )
                                                           .getValue()) >> 20 );
    }
    return FixedGlue.ZERO;
  }

  public FixedDimen getItalicCorrection( UnicodeChar uc ) {

    int cp = charPos( uc );
    if( cp >= 0 && reader.getItalicCorrection( cp ) != null ) {
      return new Dimen( (getActualSize().getValue() * reader
          .getItalicCorrection( cp ).getValue()) >> 20 );
    }
    return Dimen.ZERO_PT;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public FixedDimen getKerning( UnicodeChar uc1, UnicodeChar uc2 ) {

    int cp1 = charPos( uc1 );
    int cp2 = charPos( uc2 );

    if( cp1 < 0 || cp2 < 0 ) {
      return Dimen.ZERO_PT;
    }
    return reader.getKerning( cp1, cp2 ).toDimen( getDesignSize() );
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public UnicodeChar getLigature( UnicodeChar uc1, UnicodeChar uc2 ) {

    int cp1 = charPos( uc1 );
    int cp2 = charPos( uc2 );

    if( cp1 < 0 || cp2 < 0 ) {
      return null;
    }
    int pos = reader.getLigature( cp1, cp2 );
    if( pos < 0 ) {
      return null;
    }
    return UnicodeChar.get( Unicode.OFFSET + pos );
  }

  public String getName() {

    return fontKey.getName();
  }

  public byte[] getPfa() {

    // TODO mgn: getPfa unimplemented
    return null;
  }

  public byte[] getPfb() {

    // TODO mgn: getPfb unimplemented
    return null;
  }

  public FixedCount getScaleFactor() {

    FixedCount actualscale = actualFontKey.getCount( FontKey.SCALE );
    if( actualscale == null ) {
      return new Count( getActualSize().getValue() * 1000
                            / getDesignSize().getValue() );
    }
    return actualscale;
  }

  public FixedGlue getSpace() {

    return new Glue( getFontDimen( "SPACE" ), getFontDimen( "STRETCH" ),
                     getFontDimen( "SHRINK" ) );
  }

  public FixedGlue getWidth( UnicodeChar uc ) {

    int cp = charPos( uc );
    if( cp >= 0 && reader.getWidth( cp ) != null ) {
      return new Glue( (getActualSize().getValue() * reader.getWidth( cp )
                                                           .getValue()) >> 20 );
    }
    return FixedGlue.ZERO;
  }

  public byte[] getXtf() {

    // TODO mgn: getXtf unimplemented
    return null;
  }

  public boolean hasEncodingVector() {

    // TODO mgn: hasEncodingVector unimplemented
    return false;
  }

  public boolean hasGlyph( UnicodeChar uc ) {

    int cp = charPos( uc );
    TfmFixWord w = reader.getWidth( cp );
    /* || w.getValue() == 0 */
    // vf font can have glyphs with zero width
    return w != null;
  }

  public boolean hasMultiFonts() {

    // TODO mgn: hasMultiFonts unimplemented
    return false;
  }

  public boolean isType1() {

    // TODO mgn: isType1 unimplemented
    return false;
  }

  public boolean isXtf() {

    // TODO mgn: isXtf unimplemented
    return false;
  }

  /**
   * org.extex.font.CoreFontFactory, org.extex.font.FontKey)
   */
  public void loadFont( InputStream in, CoreFontFactory factory,
                        FontKey fontKey )
      throws CorruptFontException,
      ConfigurationException {

    this.fontKey = fontKey;

    if( fontKey == null ) {
      throw new IllegalArgumentException( "fontkey" );
    }
    if( factory == null ) {
      throw new IllegalArgumentException( "factory" );
    }

    try {

      InputStream tfmin = finder.findResource( fontKey.getName(), "tfm" );
      if( tfmin == null ) {
        throw new CorruptFontException( fontKey, localizer.format(
            "VF.TfmNotFound", fontKey.getName() ) );
      }

      reader = new TfmReader( tfmin, fontKey.getName() );

      // load vf
      vfFont = new VfFont( fontKey.getName(), in, reader );

    } catch( IOException e ) {
      throw new CorruptFontException( fontKey, e.getLocalizedMessage() );
    } catch( FontException e ) {
      throw new CorruptFontException( fontKey, e.getLocalizedMessage() );
    }

    if( fontKey.getDimen( FontKey.SIZE ) == null
        && fontKey.getCount( FontKey.SCALE ) == null ) {
      actualFontKey = factory.getFontKey( fontKey, reader.getDesignSize() );
    }
    else if( fontKey.getCount( FontKey.SCALE ) != null ) {
      // design size * scale / 1000
      actualFontKey =
          factory.getFontKey( fontKey,
                              new Dimen( reader.getDesignSize().getValue()
                                             * fontKey.getCount( FontKey.SCALE )
                                                      .getValue()
                                             / 1000 ) );
    }
    else {
      actualFontKey = fontKey;
    }

    String cs =
        reader.getCodingscheme().replaceAll( "[^A-Za-z0-9]", "" )
              .toLowerCase();

    try {
      codepointmap = U2tFactory.getInstance().loadU2t( cs, factory );
    } catch( IOException e ) {
      throw new CorruptFontException( fontKey, e.getLocalizedMessage() );
    } catch( NumberFormatException e ) {
      throw new CorruptFontException( fontKey, e.getLocalizedMessage() );
    }

    if( codepointmap == null ) {
      codepointmap = new HashMap<UnicodeChar, Integer>();
      // use default map
      for( int i = 0; i <= 255; i++ ) {
        codepointmap.put( UnicodeChar.get( i ), new Integer( i ) );
      }
    }

  }

  public void setResourceFinder( ResourceFinder finder ) {

    this.finder = finder;

  }

  public void usedCharacter( BackendCharacter bc ) {

    // TODO mgn: usedCharacter unimplemented

  }

  /**
   * org.extex.typesetter.tc.TypesettingContext,
   * org.extex.typesetter.type.node.factory.NodeFactory,
   * org.extex.typesetter.tc.TypesettingContextFactory)
   */
  public CharNode buildCharNode( UnicodeChar uc, TypesettingContext tcArg,
                                 NodeFactory factory,
                                 TypesettingContextFactory tcFactory ) {

    int cp = charPos( uc );
    if( cp < 0 && cp > 255 ) {
      // char position out of range
      return null;
    }

    // set the new font
    TypesettingContext tc = tcFactory.newInstance( tcArg,
                                                   new FontImpl( this ) );

    VfDvi2Node dvi =
        new VfDvi2Node( uc, tc, factory, tcFactory, fontfactory, cp,
                        vfFont );

    VfCommandCharacterPackets ccmd = vfFont.getChar( cp );
    if( ccmd == null ) {
      // no dvi command found
      return null;
    }
    byte[] dvicode = ccmd.getDvi();
    try {
      dvi.interpret( new RandomAccessInputArray( dvicode ) );

    } catch( Exception e ) {
      // VirtualCharnode can not be created.
      return null;
    }

    VirtualCharNode vcharNode = dvi.getVcharNode();
    if( vcharNode != null ) {
      vcharNode.setWidth( getWidth( uc ).getLength() );
      vcharNode.setHeight( getHeight( uc ).getLength() );
      vcharNode.setDepth( getDepth( uc ).getLength() );
    }
    return vcharNode;
  }

  /**
   * The font factory.
   */
  private CoreFontFactory fontfactory;

  public void setFontFactory( CoreFontFactory factory ) {

    fontfactory = factory;

  }

}
