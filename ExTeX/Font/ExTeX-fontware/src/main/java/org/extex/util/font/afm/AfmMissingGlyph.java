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

package org.extex.util.font.afm;

import org.extex.font.exception.FontException;
import org.extex.font.format.afm.AfmCharMetric;
import org.extex.font.format.afm.AfmParser;
import org.extex.font.format.texencoding.EncReader;
import org.extex.util.font.AbstractFontUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Print the missing glyphs from a font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class AfmMissingGlyph extends AbstractFontUtil {

  /**
   * parameter.
   */
  private static final int PARAMETER = 1;

  /**
   * main.
   *
   * @param args The command line.
   * @throws Exception if an error occurred.
   */
  public static void main( String[] args ) throws Exception {

    AfmMissingGlyph afm = new AfmMissingGlyph();

    if( args.length < PARAMETER ) {
      afm.getLogger().severe(
          afm.getLocalizer().format( "AfmMissingGlyph.Call" ) );
      System.exit( 1 );
    }

    String afmfile = "null";
    List<String> enclist = new ArrayList<String>();

    int i = 0;
    do {
      if( "-o".equals( args[ i ] ) || "--outdir".equals( args[ i ] ) ) {
        if( i + 1 < args.length ) {
          afm.setOutdir( args[ ++i ] );
        }

      }
      else if( "-e".equals( args[ i ] ) || "--encvector".equals( args[ i ] ) ) {
        if( i + 1 < args.length ) {
          enclist.add( args[ ++i ] );
        }

      }
      else {
        afmfile = args[ i ];
      }
      i++;
    } while( i < args.length );

    afm.doIt( afmfile, enclist );

  }

  /**
   * The list for the encoding vectors.
   */
  private List<String> enclist = new ArrayList<String>();


  public AfmMissingGlyph() {

    super( AfmMissingGlyph.class );
  }

  /**
   * do it.
   *
   * @param file The afm file name.
   * @param enc  The list with the encoding vectors.
   * @throws Exception if an error occurs.
   */
  private void doIt( String file, List<String> enc ) throws Exception {

    getLogger()
        .severe( getLocalizer().format( "AfmMissingGlyph.start", file ) );

    enclist = enc;

    InputStream afmin = null;

    File afmfile = new File( file );
    if( afmfile.canRead() ) {
      afmin = new FileInputStream( afmfile );
    }

    if( afmin == null ) {
      throw new FileNotFoundException( file );
    }

    AfmParser parser = new AfmParser( afmin );

    // read all glyphs from the encoding vectors
    List<String> readenc = new ArrayList<String>();
    readAllGlyphName( readenc );

    generateList( parser, readenc, afmfile );

  }

  /**
   * Generate the list.
   *
   * @param parser  The parser.
   * @param readenc The list with the glyph names.
   * @param afmfile The afm file.
   * @throws IOException if an io-error occurred.
   */
  private void generateList( AfmParser parser, List<String> readenc,
                             File afmfile ) throws IOException {

    File outfile =
        new File( getOutdir()
                      + File.separator
                      + afmfile.getName().replaceAll( "[Aa][Ff][Mm]",
                                                      "missing.txt" ) );

    BufferedWriter writer = new BufferedWriter( new FileWriter( outfile ) );
    writer.write( createVersion( "AfmMissingGlyph.created" ) );
    writer.write( getLocalizer().format( "AfmMissingGlyph.header",
                                         afmfile.getName() ) );

    for( String glyphname : readenc ) {
      AfmCharMetric cm = parser.getAfmCharMetric( glyphname );
      if( cm == null ) {
        writer.write( getLocalizer().format( "AfmMissingGlyph.missing",
                                             glyphname ) );
      }
    }

    writer.close();
    getLogger().severe(
        getLocalizer().format( "AfmMissingGlyph.end", outfile.getPath() ) );

  }

  /**
   * Read all glyph names from the encoding vectors.
   *
   * @param readenc The list for the names.
   * @throws IOException   if an IO-error occurred.
   * @throws FontException if a font error occurred.
   */
  private void readAllGlyphName( List<String> readenc )
      throws IOException,
      FontException {

    for( String encv : enclist ) {

      InputStream encin = null;
      File encfile = new File( encv );
      if( encfile.canRead() ) {
        encin = new FileInputStream( encfile );
      }

      if( encin == null ) {
        throw new FileNotFoundException( encv );
      }

      EncReader enc = new EncReader( encin );
      String[] table = enc.getTable();

      for( int k = 0; k < table.length; k++ ) {
        String name = table[ k ].replaceAll( "/", "" );
        if( !readenc.contains( name ) ) {
          readenc.add( name );
        }
      }
    }
  }

}
