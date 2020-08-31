/*
 * Copyright (C) 2009-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This abstract base class contains methods for testing Makeindex.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractTester {

  /**
   * The field {@code BANNER} contains the expected banner.
   */
  protected final String BANNER =
      "This is exindex version " + Makeindex.VERSION + " (revision "
          + Makeindex.REVISION + ")\n";

  /**
   * Creates a new object.
   */
  public AbstractTester() {

  }

  /**
   * Make a test instance.
   *
   * @return the new instance to be tested
   */
  protected abstract Makeindex makeInstance();

  /**
   * Run a single test case.
   *
   * @param args        the command line arguments
   * @param expectedErr the expected error output
   * @param expectedOut the expected standard output
   * @param exitCode    the expected exit code
   */
  protected void run( String[] args, String expectedErr, String expectedOut,
                      int exitCode ) {

    InputStream in = System.in;
    PrintStream err = System.err;
    PrintStream out = System.out;
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    int exit = Integer.MIN_VALUE;
    try {
      System.setIn( new ByteArrayInputStream( "".getBytes() ) );
      System.setErr( new PrintStream( errStream ) );
      System.setOut( new PrintStream( outStream ) );
      exit = makeInstance().run( args );
    } finally {
      System.setIn( in );
      System.err.flush();
      System.out.flush();
      System.setErr( err );
      System.setOut( out );
    }
    assertEquals( expectedErr, errStream.toString().replaceAll( "\r", "" )
                                        .replace( '\\', '/' ) );
    assertEquals( expectedOut, outStream.toString().replaceAll( "\r", "" ) );
    assertEquals( exitCode, exit );
  }

  /**
   * Create a file and fill it with contents; then run a usual test.
   *
   * @param args   the command line arguments
   * @param f      the name of the file
   * @param input  the input
   * @param log    the expected log output
   * @param output the expected output
   * @throws IOException in case of an I/O error
   */
  protected void runOnFile( String[] args, String f, String input, String log,
                            String output ) throws IOException {

    Writer w = new BufferedWriter( new FileWriter( f ) );
    File ind = new File( f.replaceAll( ".idx$", "" ) + ".ind" );
    try {
      w.write( input );
    } finally {
      w.close();
    }
    Reader r = null;
    try {
      run( args, log, "", 0 );
      assertTrue( "Missing " + ind.toString(), ind.exists() );
      r = new FileReader( ind );
      StringBuilder buffer = new StringBuilder();
      for( int c = r.read(); c >= 0; c = r.read() ) {
        buffer.append( (char) c );
      }
      assertEquals( output, buffer.toString() );
    } finally {
      new File( f ).delete();
      ind.delete();
      new File( f.replaceAll( ".idx$", "" ) + ".ilg" ).delete();
      if( r != null ) {
        r.close();
      }
    }
  }

}
