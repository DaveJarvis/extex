/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.parser;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.core.type.page.AbstractPage;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.makeindex.Entry;
import org.extex.exindex.makeindex.Index;
import org.extex.exindex.makeindex.Parameters;
import org.extex.exindex.makeindex.normalizer.Collator;
import org.extex.exindex.makeindex.pages.PageRangeClose;
import org.extex.exindex.makeindex.pages.PageRangeOpen;
import org.extex.exindex.makeindex.pages.PageRangeSingle;
import org.extex.exindex.makeindex.pages.Pages;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a parser for a makeindex raw index file format.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MakeindexParser implements Parser {

  /**
   * Read a character and compare it against a given value.
   *
   * @param locator the locator
   * @param ec      the expected character
   * @throws IOException                  in case of an error
   * @throws RawIndexMissingCharException in case of an error
   */
  private void expect( ReaderLocator locator, char ec )
      throws IOException,
      RawIndexMissingCharException {

    int c = locator.read();
    if( c != ec ) {
      throw new RawIndexMissingCharException( locator, (char) c, ec );
    }
  }

  /**
   * java.lang.String, org.extex.exindex.makeindex.Index, Collator)
   */
  public int[] load( Reader reader, String resource, Index index,
                     Collator collator )
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException {

    int[] count = new int[ 2 ];
    Parameters params = index.getParams();
    ReaderLocator locator = new ReaderLocator( resource, reader );
    final String keyword = params.getString( "index:keyword" );
    final char argOpen = params.getChar( "index:arg-open" );
    final char argClose = params.getChar( "index:arg-close" );
    final char escape = params.getChar( "index:escape" );
    final char quote = params.getChar( "index:quote" );
    final char encap = params.getChar( "index:encap" );
    final char level = params.getChar( "index:level" );
    final char actual = params.getChar( "index:actual" );
    final char open = params.getChar( "index:range-open" );
    final char close = params.getChar( "index:range-close" );
    char k0 = keyword.charAt( 0 );
    try {

      for( int c = locator.read(); c >= 0; c = locator.read() ) {
        if( c == k0 && scanKeyword( locator, keyword ) ) {
          String arg = scanArgument( locator, argOpen, argClose,
                                     escape, quote, index );
          String p = scanArgument( locator, argOpen, argClose,
                                   escape, quote, index );
          String enc = null;
          int x = arg.lastIndexOf( encap );
          if( x >= 0 ) {
            enc = arg.substring( x + 1 );
            arg = arg.substring( 0, x );
          }
          String[] key = splitKey( level, arg );
          String[] display = splitDisplay( key, actual, collator );
          store( index, key, display, p, enc, level, open, close );
          count[ 0 ]++;
        }
      }
    } finally {
      reader.close();
    }
    return count;
  }

  /**
   * Scan an argument.
   *
   * @param locator  the locator
   * @param argOpen  the argument open character
   * @param argClose the argument close character
   * @param escape   the escape character
   * @param quote    the quote character
   * @param index    the index
   * @return the argument found
   * @throws IOException                  in case of an error
   * @throws RawIndexEofException         in case of an unexpected EOF
   * @throws RawIndexMissingCharException in case of an error
   */
  private String scanArgument( ReaderLocator locator, char argOpen,
                               char argClose, char escape, char quote,
                               Index index )
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException {

    expect( locator, argOpen );
    StringBuilder sb = new StringBuilder();
    int level = 1;

    for( ; ; ) {
      int c = locator.read();
      if( c < 0 ) {
        throw new RawIndexEofException( locator );
      }
      else if( c == argOpen ) {
        level++;
      }
      else if( c == argClose ) {
        level--;
        if( level <= 0 ) {
          return sb.toString();
        }
      }
      else if( c == quote ) {
        int l = sb.length();
        if( l <= 0 || sb.charAt( l - 1 ) != escape ) {
          c = locator.read();
          if( c < 0 ) {
            throw new RawIndexEofException( locator );
          }
        }
      }
      sb.append( (char) c );
    }
  }

  /**
   * Scan for the next occurrence of a keyword.
   *
   * @param r       the reader
   * @param keyword the keyword to read
   * @return {@code true} iff the keyword has been found
   * @throws IOException in case of an I/O error
   */
  private boolean scanKeyword( ReaderLocator r, String keyword )
      throws IOException {

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
   * Split off the display valued from the key.
   *
   * @param key      the key
   * @param actual   the actual character
   * @param collator the collator or {@code null}
   * @return the display value
   */
  private String[] splitDisplay( String[] key, char actual,
                                 Collator collator ) {

    String[] display = new String[ key.length ];
    for( int i = 0; i < key.length; i++ ) {
      int x = key[ i ].indexOf( actual );
      String a;
      if( x >= 0 ) {
        a = key[ i ].substring( x + 1 );
        if( collator != null ) {
          key[ i ] = collator.collate( key[ i ].substring( 0, x ) );
        }
        else {
          key[ i ] = key[ i ].substring( 0, x );
        }
      }
      else {
        a = key[ i ];
        if( collator != null ) {
          key[ i ] = collator.collate( key[ i ] );
        }
      }
      display[ i ] = a;
    }
    return display;
  }

  /**
   * Split the key into an array.
   *
   * @param level the level separating character
   * @param key   the key
   * @return the key array
   */
  private String[] splitKey( char level, String key ) {

    List<String> list = new ArrayList<String>();
    int a = 0;
    for( int i = key.indexOf( level, a ); i >= 0; i = key.indexOf( level,
                                                                   a ) ) {
      list.add( key.substring( a, i ) );
      a = i + 1;
    }
    list.add( key.substring( a ) );
    return list.toArray( new String[ list.size() ] );
  }

  /**
   * Store an entry in the index.
   *
   * @param index   the index
   * @param key     the key
   * @param display the display value
   * @param p       the page
   * @param encap   the page encapsulator or {@code null} for none
   * @param open    the range open character
   * @param close   the range close character
   */
  private void store( Index index, String[] key, String[] display, String p,
                      String encap, char level, char open, char close ) {

    PageReference from = AbstractPage.get( p, null );
    Pages page;
    if( encap == null || encap.equals( "" ) ) {
      page = new PageRangeSingle( from, encap );
    }
    else if( encap.charAt( 0 ) == open ) {
      String enc = encap.length() == 1 ? null : encap.substring( 1 );
      page = new PageRangeOpen( from, enc );
    }
    else if( encap.charAt( 0 ) == close ) {
      String enc = encap.length() == 1 ? null : encap.substring( 1 );
      page = new PageRangeClose( from, enc );
    }
    else {
      page = new PageRangeSingle( from, encap );
    }
    index.add( new Entry( key, display, page ) );
  }

}
