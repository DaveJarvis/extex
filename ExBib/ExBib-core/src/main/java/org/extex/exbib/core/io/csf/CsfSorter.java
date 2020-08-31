/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.csf;

import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.db.sorter.Startable;
import org.extex.exbib.core.exceptions.ExBibCsfNotFoundException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.PropertyAware;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * This is a sorter which used a csf file to read a configuration from.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CsfSorter
    implements
    Comparator<Entry>,
    Sorter,
    Serializable,
    ResourceAware,
    PropertyAware,
    Startable {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2009L;

  /**
   * The field {@code ord} contains the order mapping.
   */
  private final int[] ord = new int[ 256 ];

  /**
   * The field {@code upper} contains the uppercase mapping.
   */
  private final char[] upper = new char[ 256 ];

  /**
   * The field {@code lower} contains the lowercase mapping.
   */
  private final char[] lower = new char[ 256 ];

  /**
   * The field {@code resource} contains the name of the resource to read.
   */
  private String resource = null;

  /**
   * The field {@code finder} contains the resource finder.
   */
  private transient ResourceFinder finder;

  /**
   * The field {@code properties} contains the properties.
   */
  private Properties properties;


  public CsfSorter() {

    for( int i = 0; i < 128; i++ ) {
      ord[ i ] = i;
      lower[ i ] = (char) i;
      upper[ i ] = (char) i;
    }
    for( int i = 128; i < 256; i++ ) {
      ord[ i ] = Integer.MAX_VALUE;
      lower[ i ] = (char) i;
      upper[ i ] = (char) i;
    }
  }

  /**
   * Creates a new object.
   *
   * @param resource the name of the resource to read
   */
  public CsfSorter( String resource ) {

    this();
    this.resource = resource;
  }

  public int compare( Entry a, Entry b ) {

    String ka = a.getSortKey();

    if( ka == null ) {
      ka = a.getKey();
    }

    String kb = b.getSortKey();

    if( kb == null ) {
      kb = b.getKey();
    }

    int ia = 0;
    int ib = 0;
    int ca;
    int cb;
    do {
      for( ca = -1; ia < ka.length() && ca < 0; ia++ ) {
        ca = ord[ ka.charAt( ia ) ];
      }
      for( cb = -1; ib < kb.length() && cb < 0; ib++ ) {
        cb = ord[ kb.charAt( ib ) ];
      }
    } while( ca >= 0 && ca == cb );

    return ca - cb;
  }

  /**
   * Get the lower counterpart for a character. If a character has no such
   * counterpart the character itself is returned.
   *
   * @param c the character
   * @return the lower case character
   */
  int getLower( char c ) {

    return lower[ c ];
  }

  /**
   * Getter for order.
   *
   * @param index the index in the range 0 &le; index &le; 255
   * @return the order
   */
  public int getOrder( int index ) {

    return ord[ index ];
  }

  /**
   * Get the upper counterpart for a character. If a character has no such
   * counterpart the character itself is returned.
   *
   * @param c the character
   * @return the uppercase character
   */
  int getUpper( char c ) {

    return upper[ c ];
  }

  /**
   * Setter for the lower case counterpart of a upper case character.
   *
   * @param uc the upper case character
   * @param lc the lower case character
   */
  public void setLower( char uc, char lc ) {

    lower[ uc ] = lc;
  }

  /**
   * Setter for the order mapping.
   *
   * @param c  the character
   * @param on the ordinal number
   */
  public void setOrder( char c, int on ) {

    ord[ c ] = on;
  }

  public void setProperties( Properties properties ) {

    this.properties = properties;
  }

  public void setResourceFinder( ResourceFinder f ) {

    this.finder = f;
  }

  /**
   * Setter for the upper case counterpart of a lower case character.
   *
   * @param lc the lower case character
   * @param uc the upper case character
   */
  public void setUpper( char lc, char uc ) {

    upper[ lc ] = uc;
  }

  public void sort( List<Entry> list ) throws ConfigurationException {

    Collections.sort( list, this );
  }

  public void start() throws ExBibException {

    if( resource == null || "".equals( resource ) ) {
      return;
    }

    InputStream is = finder.findResource( resource, "csf" );
    if( is == null ) {
      throw new ExBibCsfNotFoundException( resource );
    }
    try {
      try {
        String encoding =
            properties.getProperty( ExBib.PROP_CSF_ENCODING );
        new CsfReader().read( encoding == null
                                  ? new InputStreamReader( is )
                                  : new InputStreamReader( is, encoding ),
                              this );
      } finally {
        is.close();
      }
    } catch( IOException e ) {
      throw new ExBibException( e );
    }

  }

  /**
   * Translate a character to lower case.
   *
   * @param c the input character
   * @return the lower-cased character or c itself if no counterpart is known
   */
  public char toLowerCase( char c ) {

    return lower[ c ];
  }

  /**
   * Translate a character to upper case.
   *
   * @param c the input character
   * @return the upper-cased character or c itself if no counterpart is known
   */
  public char toUpperCase( char c ) {

    return upper[ c ];
  }

}
