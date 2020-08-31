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

package org.extex.exindex.core.finder;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This resource finder imitating the search strategy of Xindy.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SearchPath implements ResourceFinder {

  /**
   * The field {@code LOCLIZER} contains the localizer.
   */
  private static final Localizer LOCLIZER =
      LocalizerFactory.getLocalizer( SearchPath.class );

  /**
   * The field {@code fallback} contains the fallback resource finder.
   */
  private final ResourceFinder fallback;

  /**
   * The field {@code dirs} contains the list of directories.
   */
  private String[] dirs = new String[]{""};

  /**
   * The field {@code logger} contains the logger for tracing.
   */
  private Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param fallback the fallback resource finder; it can be {@code null}
   *                 for none
   */
  public SearchPath( ResourceFinder fallback ) {

    this.fallback = fallback;
  }

  public void enableTracing( boolean flag ) {

    this.logger = (flag ? Logger.getLogger( getClass().getName() ) : null);
  }

  /**
   * java.lang.String)
   */
  public NamedInputStream findResource( String name, String type )
      throws ConfigurationException {

    for( String d : dirs ) {
      if( d == null ) {
        if( fallback != null ) {
          if( logger != null ) {
            logger.fine( LOCLIZER.format( "UsingFallback" ) );
          }
          NamedInputStream stream = fallback.findResource( name, type );
          if( stream != null ) {
            return stream;
          }
        }
      }
      else {
        File f = new File( d, name );
        if( logger != null ) {
          logger.fine( LOCLIZER.format( "Trying", f.toString() ) );
        }
        if( f.canRead() ) {
          InputStream stream;
          try {
            stream = new FileInputStream( f );
            if( stream != null ) {
              if( logger != null ) {
                logger.fine( LOCLIZER.format( "Found", f
                    .toString() ) );
              }
              return new NamedInputStream( stream, f.toString() );
            }
          } catch( FileNotFoundException e ) {
            // keep on trying
          }
        }
      }
    }
    logger.fine( LOCLIZER.format( "Failed", name, type ) );
    return null;
  }

  /**
   * Getter for the path.
   *
   * @return a copy of the path in use
   */
  public String[] get() {

    return dirs.clone();
  }

  /**
   * Set the search path. The argument is a string of colon separated
   * directories to be prepended before the file name to be sought. These
   * directories can either be absolute or relative. If the last character is
   * a colon then the fallback resources finder is used at the end.
   *
   * @param path the path
   */
  public void set( String path ) {

    this.dirs = path.split( ":" );
    if( path.endsWith( ":" ) ) {
      this.dirs[ this.dirs.length - 1 ] = null;
    }
    setHome();
  }

  /**
   * Set the search path. The argument is a list of directories to be
   * prepended before the file name to be sought. These directories can either
   * be absolute or relative. If a directory is {@code null} then the
   * fallback resources finder is used instead.
   *
   * @param path the path
   */
  public void set( String[] path ) {

    dirs = path.clone();
    setHome();
  }

  /**
   * Use the home directory for leading tilde characters.
   */
  private void setHome() {

    for( int i = 0; i < dirs.length; i++ ) {
      if( dirs[ i ].startsWith( "~" ) ) {
        dirs[ i ] =
            System.getProperty( "user.home" ) + dirs[ i ].substring( 1 );
      }
    }
  }
}
