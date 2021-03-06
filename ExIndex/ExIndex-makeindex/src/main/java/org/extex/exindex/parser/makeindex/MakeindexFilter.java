/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.parser.makeindex;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.lisp.parser.ResourceLocator;
import org.extex.exindex.makeindex.normalizer.Collator;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MakeindexFilter extends Reader {

  /**
   * The field {@code in} contains the input.
   */
  private LineNumberReader in;

  /**
   * The field {@code buffer} contains the intermediate buffer.
   */
  private final StringBuilder buffer = new StringBuilder();

  /**
   * The field {@code keyword} contains the keyword.
   */
  private final String keyword = "\\indexentry";

  /**
   * The field {@code argOpen} contains the argument open character.
   */
  private final char argOpen = '{';

  /**
   * The field {@code argClose} contains the argument close character.
   */
  private final char argClose = '}';

  /**
   * The field {@code escape} contains the escape character.
   */
  private final char escape = '"';

  /**
   * The field {@code quote} contains the quote character.
   */
  private final char quote = '\\';

  /**
   * The field {@code encap} contains the encapsulation character.
   */
  private final char encap = '|';

  /**
   * The field {@code level} contains the level separation characters.
   */
  private final char level = '!';

  /**
   * The field {@code actual} contains the actual character.
   */
  private final char actual = '@';

  /**
   * Creates a new object.
   *
   * @param in the input reader
   */
  public MakeindexFilter( Reader in ) {

    this.in = new LineNumberReader( in );
  }

  @Override
  public void close() throws IOException {

    in.close();
    in = null;
  }

  /**
   * @param r       the reader
   * @param locator the locator
   * @param ec      the expected character
   * @throws IOException                  in case of an error
   * @throws RawIndexMissingCharException in case of an error
   */
  private void expect( LineNumberReader r, ResourceLocator locator, char ec )
      throws IOException,
      RawIndexMissingCharException {

    int c = r.read();
    if( c != ec ) {
      throw new RawIndexMissingCharException( locator, (char) c, ec );
    }
  }

  /**
   * Fill the buffer.
   *
   * @param locator  the locator
   * @param collator the collator
   * @return {@code true} at eof
   * @throws IOException                  in case of an error
   * @throws RawIndexEofException         in case of an error
   * @throws RawIndexMissingCharException in case of an error
   */
  private boolean fillBuffer( ResourceLocator locator, Collator collator )
      throws IOException,
      RawIndexMissingCharException,
      RawIndexEofException {

    char k0 = keyword.charAt( 0 );

    for( int c = in.read(); c >= 0; c = in.read() ) {
      if( c == k0 && scanKeyword( in, keyword ) ) {
        String arg = scanArgument( locator );
        String p = scanArgument( locator );
        String enc = null;
        int x = arg.lastIndexOf( encap );
        if( x >= 0 ) {
          enc = arg.substring( x + 1 );
          arg = arg.substring( 0, x );
        }
        x = arg.indexOf( actual );
        String a;
        if( x >= 0 ) {
          a = arg.substring( x + 1 );
          arg = arg.substring( 0, x );
        }
        else {
          a = arg;
        }
        if( collator != null ) {
          a = collator.collate( a );
        }
        store( arg, p, enc, a );
        return false;
      }
    }
    in.close();
    in = null;
    return true;
  }

  @Override
  public int read( char[] cbuf, int off, int len ) throws IOException {

    if( buffer.length() <= 0 ) {
      try {
        if( in == null || fillBuffer( null, null ) ) {
          return -1;
        }
      } catch( RawIndexMissingCharException | RawIndexEofException e ) {
        throw new IOException( e.getLocalizedMessage() );
      }
    }

    int length = buffer.length();
    int bp = 0;
    int i;
    for( i = 0; i < len && bp < length; i++ ) {
      cbuf[ off + i ] = buffer.charAt( bp++ );
    }

    buffer.delete( 0, i );
    return i;
  }

  /**
   * @param locator the locator
   * @return the argument found
   * @throws IOException                  in case of an error
   * @throws RawIndexMissingCharException in case of an error
   * @throws RawIndexEofException         in case of an unexpected EOF
   */
  private String scanArgument( ResourceLocator locator )
      throws IOException,
      RawIndexMissingCharException,
      RawIndexEofException {

    expect( in, locator, argOpen );
    StringBuilder sb = new StringBuilder();
    int depth = 1;

    for( ; ; ) {
      int c = in.read();
      if( c < 0 ) {
        throw new RawIndexEofException( locator );
      }
      else if( c == argOpen ) {
        depth++;
      }
      else if( c == argClose ) {
        depth--;
        if( depth <= 0 ) {
          return sb.toString();
        }
      }
      else if( c == quote ) {
        int l = sb.length();
        if( l <= 0 || sb.charAt( l - 1 ) != escape ) {
          c = in.read();
          if( c < 0 ) {
            throw new RawIndexEofException( locator );
          }
        }
      }
      sb.append( (char) c );
    }
  }

  /**
   * @param r       the reader
   * @param keyword the keyword to read
   * @return {@code true} iff the keyword has been found
   * @throws IOException in case of an I/O error
   */
  private boolean scanKeyword( Reader r, String keyword ) throws IOException {

    int length = keyword.length();
    for( int i = 1; i < length; i++ ) {
      int c = r.read();
      if( c != keyword.charAt( i ) ) {
        return false;
      }
    }

    return true;
  }

  /**
   * @param a1      TODO
   * @param p       the page
   * @param encap   the page encapsulator or {@code null} for none
   * @param display the display representation
   */
  private void store( String a1, String p, String encap, String display ) {

    String key = a1;
    List<String> list = new ArrayList<String>();
    int a = 0;
    for( int i = key.indexOf( level, a ); i >= 0; i = key.indexOf( level,
                                                                   a ) ) {
      list.add( key.substring( a, i ) );
      a = i + 1;
    }
    list.add( key );

    buffer.append( "(indexentry :key (" );
    for( String k : list ) {
      buffer.append( '"' );
      buffer.append( k.replaceAll( "\\[\"]", "\\\\\\1" ).replaceAll( "\n",
                                                                     "\\n" ) );
      buffer.append( '"' );
      buffer.append( ' ' );
    }
    buffer.append( ") :print (" );
    // TODO
    buffer.append( ") " );
    if( encap != null ) {
      buffer.append( ":attr \"" );
      buffer.append( encap.replaceAll( "\\[\"]", "\\\\\\1" ).replaceAll(
          "\n", "\\n" ) );
      buffer.append( "\" " );
    }
    buffer.append( ":locref " );

    buffer.append( "\"" );
    buffer.append( "\"" );

    buffer.append( ")" );
    // TODO
  }
}
