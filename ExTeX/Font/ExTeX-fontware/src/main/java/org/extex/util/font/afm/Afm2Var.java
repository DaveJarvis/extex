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

import org.extex.font.format.afm.AfmParser;
import org.extex.util.font.AbstractFontUtil;

import java.io.*;

/**
 * Extract the afm information's the shell variables.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class Afm2Var extends AbstractFontUtil {

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

    Afm2Var afm = new Afm2Var();

    if( args.length < PARAMETER ) {
      afm.getLogger().severe( afm.getLocalizer().format( "Xtf2Var.Call" ) );
      System.exit( 1 );
    }

    String afmfile = "null";

    int i = 0;
    do {
      if( "-o".equals( args[ i ] ) || "--outdir".equals( args[ i ] ) ) {
        if( i + 1 < args.length ) {
          afm.setOutdir( args[ ++i ] );
        }

      }
      else {
        afmfile = args[ i ];
      }
      i++;
    } while( i < args.length );

    afm.doIt( afmfile );

  }


  public Afm2Var() {

    super( Afm2Var.class );
  }

  /**
   * do it.
   *
   * @param file The afm file name.
   * @throws Exception if an error occurs.
   */
  public void doIt( String file ) throws Exception {

    getLogger().severe( getLocalizer().format( "Xtf2Var.start", file ) );

    InputStream afmin = null;

    // find directly the afm file.
    File afmfile = new File( file );

    if( afmfile.canRead() ) {
      afmin = new FileInputStream( afmfile );
    }

    if( afmin == null ) {
      throw new FileNotFoundException( file );
    }

    AfmParser parser = new AfmParser( afmin );

    String outfile =
        getOutdir()
            + File.separator
            + afmfile.getName()
                     .replaceAll( "\\.[aA][fF][mM]", ".sh" );

    BufferedWriter writer = new BufferedWriter( new FileWriter( outfile ) );

    writer.write( createVersion( "Xtf2Var.created" ) );
    writer.write( getLocalizer().format( "Xtf2Var.NUMBEROFGLYPHS",
                                         String.valueOf( parser.getNumberOfGlyphs() ) ) );
    writer.write( getLocalizer().format( "Xtf2Var.FONTNAME",
                                         parser.getFontname() ) );
    writer.write( getLocalizer().format( "Xtf2Var.FAMILYNAME",
                                         parser.getFamilyname() ) );
    writer.write( getLocalizer().format( "Xtf2Var.FULLNAME",
                                         parser.getFullname() ) );
    writer.write( getLocalizer().format( "Xtf2Var.COMMENT",
                                         parser.getComment() ) );
    writer.write( getLocalizer()
                      .format( "Xtf2Var.NOTICE", parser.getNotice() ) );

    writer.close();

    getLogger().severe( getLocalizer().format( "Xtf2Var.end", outfile ) );

  }

}
