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

package org.extex.font.format.tfm;

import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.ResourceFinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Factory for the Unicode to tex font mapping.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class U2tFactory {

  /**
   * The own factory.
   */
  private static U2tFactory factory;

  /**
   * Returns the singleton instance.
   *
   * @return the singleton instance.
   */
  public static U2tFactory getInstance() {

    if( factory == null ) {
      factory = new U2tFactory();
    }
    return factory;
  }

  /**
   * Map for caching (u2t).
   */
  private final Map<String, Map<UnicodeChar, Integer>> cacheu2t =
      new WeakHashMap<String, Map<UnicodeChar, Integer>>();

  /**
   * Map for caching (t2u).
   */
  private final Map<String, Map<Integer, UnicodeChar>> cachet2u =
      new WeakHashMap<String, Map<Integer, UnicodeChar>>();

  /**
   * Creates a new object only with getInstance().
   */
  private U2tFactory() {

  }

  /**
   * Returns the t2u map, or {@code null}, if the property file is not
   * found.
   *
   * @param name   the name of the property.
   * @param finder the resource finder.
   * @return the t2u map, or {@code null}, if the property file is not
   * found.
   * @throws IOException            if a io error occurred.
   * @throws ConfigurationException from the configuration system.
   * @throws NumberFormatException  if a parse error occurred.
   */
  public Map<Integer, UnicodeChar> loadT2u( String name, ResourceFinder finder )
      throws IOException,
      ConfigurationException,
      NumberFormatException {

    if( name == null || finder == null ) {
      return null;
    }

    // in cacheu2t?
    Map<Integer, UnicodeChar> codepointmapt2u = cachet2u.get( name );

    if( codepointmapt2u != null ) {
      return codepointmapt2u;
    }

    // load u2t map
    Map<UnicodeChar, Integer> codepointmap = loadU2t( name, finder );

    if( codepointmap != null ) {

      codepointmapt2u = new HashMap<Integer, UnicodeChar>();

      for( UnicodeChar key : codepointmap.keySet() ) {
        codepointmapt2u.put( codepointmap.get( key ), key );

      }
      cachet2u.put( name, codepointmapt2u );
      return codepointmapt2u;
    }

    return null;
  }

  /**
   * Returns the u2t map, or {@code null}, if the property file is not
   * found.
   *
   * @param name   the name of the property.
   * @param finder the resource finder.
   * @return the u2t map, or {@code null}, if the property file is not
   * found.
   * @throws IOException            if a io error occurred.
   * @throws ConfigurationException from the configuration system.
   * @throws NumberFormatException  if a parse error occurred.
   */
  public Map<UnicodeChar, Integer> loadU2t( String name, ResourceFinder finder )
      throws IOException,
      ConfigurationException,
      NumberFormatException {

    if( name == null || finder == null ) {
      return null;
    }

    // in cacheu2t?
    Map<UnicodeChar, Integer> codepointmap = cacheu2t.get( name );

    if( codepointmap != null ) {
      return codepointmap;
    }

    codepointmap = new HashMap<UnicodeChar, Integer>();

    InputStream u2tin = finder.findResource( name, "u2t" );

    if( u2tin != null ) {
      Properties u2tprops = new Properties();
      u2tprops.load( u2tin );
      u2tin.close();

      Enumeration<?> u2tenum = u2tprops.keys();
      while( u2tenum.hasMoreElements() ) {
        String key = (String) u2tenum.nextElement();
        String value = u2tprops.getProperty( key );

        UnicodeChar uc = UnicodeChar.get( Integer.parseInt( key, 16 ) );
        int texpos = Integer.parseInt( value, 16 );
        codepointmap.put( uc, new Integer( texpos ) );
      }
      cacheu2t.put( name, codepointmap );
      return codepointmap;
    }
    return null;
  }

}
