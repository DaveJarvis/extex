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

package org.extex.backend.documentWriter.itextpdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.extex.core.UnicodeChar;
import org.extex.font.BackendFont;
import org.extex.font.FontKey;
import org.extex.font.unicode.GlyphName;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

import java.io.IOException;
import java.util.List;
import java.util.WeakHashMap;
import java.util.logging.Logger;

/**
 * Factory for fonts to use it with iText.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class PdfFontFactory {

  /**
   * The map for the fonts.
   */
  private static WeakHashMap<String, BaseFont> fonts;

  /**
   * The field {@code localizer} contains the localizer. It is initiated with
   * a localizer for the name of this class.
   */
  private static final Localizer localizer = LocalizerFactory
      .getLocalizer( PdfFontFactory.class );

  /**
   * The logger.
   */
  private static Logger logger;

  /**
   * Returns the font for the pdf backend.
   *
   * @param backendfont The backend font.
   * @param uc          The unicode character.
   * @return Returns the font for the pdf backend.
   * @throws DocumentException if a document error occurred.
   * @throws IOException       if a io-error occurred.
   */
  public static BaseFont getFont( BackendFont backendfont, UnicodeChar uc )
      throws DocumentException,
      IOException {

    if( backendfont == null ) {
      throw new IllegalArgumentException( "backendfont" );
    }

    if( fonts == null ) {
      fonts = new WeakHashMap<String, BaseFont>();
    }
    FontKey key = backendfont.getActualFontKey();
    String encodingVectorKey = " encVec: -";
    int encpos = -1;
    // TODO gene: disabled to get it compile
    // if (backendfont.hasMultiFonts() && uc != null) {
    // encpos = backendfont.getEncodingForChar(uc.getCodePoint());
    // encodingVectorKey = " encVec: " + String.valueOf(encpos);
    // }
    BaseFont font = fonts.get( key.toString() + encodingVectorKey );

    if( font != null ) {
      return font;
    }

    if( backendfont.isXtf() ) {
      byte[] data = backendfont.getXtf();
      // TODO mgn: check encoding
      font =
          BaseFont
              .createFont(
                  key.getName() + ".ttf" /* name */,
                  BaseFont.IDENTITY_H /* BaseFont.CP1252 *//* encoding */,
                  true /* embedded */, true /* cached */, data /*
                   * ttf,afm
                   */,
                  null /* pfb */ );
      logInfo( localizer.format( "PdfFontFactory.useFont", "XTF",
                                 key.getName() ) );
    }
    if( backendfont.isType1() ) {
      byte[] afmdata = null;
      // TODO gene: disabled to get it compile
      // afmdata = backendfont.getAfm();
      byte[] pfbdata = backendfont.getPfb();

      if( afmdata != null && pfbdata != null ) {
        String encoding = "";
        if( encpos >= 0 ) {
          // Example for a "full" encoding for a Type1 Tex font:
          // "# full
          // 'A' nottriangeqlleft 0041
          // 'B' dividemultiply 0042
          // 32 space 0020"

          List<String[]> enclist = null;
          // TODO gene: disabled to get it compile
          // enclist = backendfont.getEncodingVectors();
          String[] enc = enclist.get( encpos );
          if( enc != null ) {
            StringBuilder buf = new StringBuilder( "# full" );
            GlyphName glyphName = GlyphName.getInstance();
            for( int i = 0; i < enc.length; i++ ) {
              if( !enc[ i ].equals( ".notdef" ) ) {
                buf.append( " " ).append( i ).append( " " );
                buf.append( enc[ i ] ).append( " " );
                UnicodeChar uni = glyphName.getUnicode( enc[ i ] );
                if( uni != null ) {
                  buf.append( Integer.toHexString( uni
                                                       .getCodePoint() ) );
                }
                else {
                  // uses the number
                  buf.append( Integer.toHexString( i ) );
                }
              }
            }
            encoding = buf.toString();
          }
        }

        font =
            BaseFont.createFont( key.getName() + ".afm" /* name */,
                                 encoding /* BaseFont.CP1252 *//* encoding */,
                                 true /* embedded */, true /* cached */,
                                 afmdata /* ttf,afm */, pfbdata /* pfb */ );
        logInfo( localizer.format( "PdfFontFactory.useFont", "Type1",
                                   key.getName() ) );
      }
      else {
        logSevere( localizer.format( "PdfFontFactory.missingAfmPfb",
                                     key.getName() ) );
      }
    }
    if( font == null ) {
      font =
          BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252,
                               BaseFont.EMBEDDED );
      logSevere( localizer.format( "PdfFontFactory.defaultFont",
                                   key.getName() ) );
    }
    // store it
    fonts.put( key.toString() + encodingVectorKey, font );

    return font;
  }

  /**
   * Log a info message.
   *
   * @param msg The message.
   */
  private static void logInfo( String msg ) {

    if( logger != null ) {
      logger.info( msg );
    }
  }

  /**
   * Log a severe message.
   *
   * @param msg The message.
   */
  private static void logSevere( String msg ) {

    if( logger != null ) {
      logger.severe( msg );
    }
  }

  /**
   * Setter for logger.
   *
   * @param logger the logger to set
   */
  public static void setLogger( Logger logger ) {

    PdfFontFactory.logger = logger;
  }

}
