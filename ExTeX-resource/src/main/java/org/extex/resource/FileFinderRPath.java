/*
 * Copyright (C) 2004-2008 The ExTeX Group and individual authors listed below
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

package org.extex.resource;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.io.NamedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * This file finder search recursively in a directory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FileFinderRPath extends AbstractFinder
    implements
    ResourceFinder,
    LogEnabled,
    PropertyAware {

  /**
   * The field {@code properties} contains the properties.
   */
  private Properties properties = null;

  /**
   * Creates a new object.
   *
   * @param configuration the encapsulated configuration object
   */
  public FileFinderRPath( Configuration configuration ) {

    super( configuration );
  }

  /**
   * Find a file recursively.
   *
   * @param fpath path for searching
   * @param name  the file name
   * @param cfg   the configuration
   * @return Returns the input stream
   */
  private NamedInputStream findFile( File fpath, String name,
                                     Configuration cfg ) {

    File file;
    for( String ext : cfg.getValues( "extension" ) ) {
      file =
          new File( fpath, name + (ext.equals( "" ) ? "" : ".")
              + ext.replaceAll( "^\\.", "" ) );
      trace( "Try", file.toString() );
      if( file.canRead() ) {
        try {
          InputStream stream = new FileInputStream( file );
          trace( "Found", file.toString() );
          return new NamedInputStream( stream, file.toString() );
        } catch( FileNotFoundException e ) {
          // ignore unreadable files
          continue;
        }
      }
    }

    // call findFile recursively
    File[] files = fpath.listFiles();
    for( int i = 0; i < files.length; i++ ) {
      if( files[ i ].isDirectory() ) {
        NamedInputStream in = findFile( files[ i ], name, cfg );
        // found ??
        if( in != null ) {
          return in;
        }
      }
    }
    // nothing found!
    return null;

  }

  /**
   * java.lang.String)
   */
  public NamedInputStream findResource( String name, String type )
      throws ConfigurationException {

    Configuration config = getConfiguration();
    Configuration cfg = config.findConfiguration( type );
    if( cfg == null ) {
      String t = config.getAttribute( "default" );
      if( t == null ) {
        throw new ConfigurationMissingAttributeException( "default",
                                                          config );
      }
      cfg = config.getConfiguration( t );
      trace( "ConfigurationNotFound", type, t );
    }

    trace( "Searching", name, type );

    Iterator<Configuration> iterator = cfg.iterator( "path" );
    while( iterator.hasNext() ) {
      Configuration c = iterator.next();
      String prop = c.getAttribute( "property" );
      String path = null;
      if( prop != null ) {
        path = properties.getProperty( prop );
      }
      else {
        path = c.getValue();
      }

      // only for directories
      File fpath = new File( path );
      if( !fpath.isDirectory() ) {
        trace( "NoPath", fpath.toString() );
        return null;
      }

      NamedInputStream in = findFile( fpath, name, cfg );
      if( in != null ) {
        return in;
      }
    }
    return null;
  }

  public void setProperties( Properties prop ) {

    properties = prop;
  }

}
