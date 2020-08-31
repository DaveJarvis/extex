/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.main;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains some utility methods for testing.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class TRunner {

  /**
   * Read a text file and return its contents.
   *
   * @param file the file to read
   * @return the contents of the file
   * @throws IOException in case of an error
   */
  public static String readFile( File file ) throws IOException {

    StringBuilder sb = new StringBuilder();
    FileInputStream stream = new FileInputStream( file );
    Reader r = new InputStreamReader( stream, StandardCharsets.UTF_8 );
    try {
      for( int c = r.read(); c >= 0; c = r.read() ) {
        if( c != '\r' ) {
          sb.append( (char) c );
        }
      }
    } finally {
      r.close();
      stream.close();
    }

    return sb.toString();
  }

  /**
   * Run a test case.
   *
   * @param aux    the aux file
   * @param result the file containing the expected result
   * @param args   the additional arguments inserted before the aux file name
   * @throws IOException in case of an error
   */
  public static void runTest( File aux, File result, String... args )
      throws IOException {

    if( !result.exists() ) {
      assertTrue( result.toString() + " reference not found", false );
    }
    File bbl = new File( aux.getParent(),
                         aux.getName().replaceAll( ".aux$", ".bbl" ) );

    PrintStream err = System.err;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      String[] a = new String[ args.length + 2 ];
      int i = 0;
      a[ i++ ] = "-q";
      for( String s : args ) {
        a[ i++ ] = s;
      }
      a[ i++ ] = aux.toString();

      System.setErr( new PrintStream( baos ) );

      int code = ExBibMain.commandLine( a );
      assertEquals( "No error", "", baos.toString() );
      assertEquals( 0, code );

      assertEquals( readFile( result ), readFile( bbl ) );

    } finally {
      System.setErr( err );
      if( bbl.exists() && !bbl.delete() ) {
        assertTrue( bbl.toString() + ": deletion failed", false );
      }
      File f = new File( aux.getParent(),
                         aux.getName().replaceAll( ".aux$", ".blg" ) );
      if( f.exists() && !f.delete() ) {
        assertTrue( f.toString() + ": deletion failed", false );
      }

    }
  }

  /**
   * Create an aux file and run a test case.
   *
   * @param style    the style file
   * @param data     the data file
   * @param citation the citations (comma separated keys
   * @param result   the result file
   * @param args     the additional arguments
   * @throws IOException in case of an error
   */
  public static void runTest( String style, String data, String citation,
                              File result, String... args ) throws IOException {

    File aux = new File( "test.aux" );
    FileWriter out = new FileWriter( aux );
    try {
      out.write( "\\relax\n"
                     + "\\citation{" + citation + "}\n"
                     + "\\bibstyle{" + style + "}\n"
                     + "\\bibdata{" + data + "}\n" );
    } finally {
      out.close();
    }

    try {
      runTest( aux, result, args );
    } finally {
      if( aux.exists() && !aux.delete() ) {
        assertTrue( aux.toString() + ": deletion failed", false );
      }
    }
  }

  /**
   * Creates a new object.
   */
  private TRunner() {

  }

}
