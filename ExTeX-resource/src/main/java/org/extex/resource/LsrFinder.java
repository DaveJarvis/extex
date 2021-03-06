/*
 * Copyright (C) 2004-2010 The ExTeX Group and individual authors listed below
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
import org.extex.framework.configuration.exception.*;
import org.extex.resource.io.NamedInputStream;

import java.io.*;
import java.util.*;

/**
 * This resource finder searches a file in a {@code ls-R} file database as
 * present in a texmf tree. For this purpose the {@code ls-R} file databases
 * found are read and stored internally.
 *
 * <h2>Configuration</h2> The lsr finder can be configured to influence its
 * actions. The following example shows a configuration for a lsr finder:
 *
 * <pre>
 * &lt;Finder class=&quot;de.dante.util.resource.LsrFinder&quot;
 *          default=&quot;default&quot;
 *          capacity=&quot;1234567&quot;
 *          trace=&quot;false&quot;&gt;
 *   &lt;path property=&quot;extex.font.path&quot;&gt;&lt;/path&gt;
 *   &lt;path property=&quot;texmf.path&quot;&gt;&lt;/path&gt;
 *   &lt;tfm&gt;&lt;extension&gt;.tfm&lt;/extension&gt;&lt;/tfm&gt;
 *   &lt;efm&gt;&lt;extension&gt;.efm&lt;/extension&gt;&lt;/efm&gt;
 *   &lt;pfb&gt;&lt;extension&gt;.pfb&lt;/extension&gt;&lt;/pfb&gt;
 *   &lt;ttf&gt;&lt;extension&gt;.ttf&lt;/extension&gt;&lt;/ttf&gt;
 *   &lt;default&gt;&lt;extension/&gt;&lt;/default&gt;
 * &lt;/Finder&gt;
 * </pre>
 *
 * <p>
 * Whenever a resource is sought the first step is to ensure that the file
 * databases are read in. For this purpose the {@code path} tag is used. The
 * {@code path} tags name directories which may contain file databases. The
 * file databases have a fixed name {@code ls-R}.
 * </p>
 * <p>
 * {@code path} can carry the attribute {@code property}. In this case the
 * value is ignored and the value is taken from the property named in the
 * attribute. Otherwise the value of the tag is taken as path. The value taken
 * from the property can contain several paths. They are separated by the
 * separator specified for the platform. For instance on windows the separator
 * {@code ;} is used and on Unix the separator {@code :} is used.
 * </p>
 * <p>
 * {@code path} can carry the attribute {@code env}. In this case the value is
 * ignored and the value is taken from the environment variable named in the
 * attribute. Otherwise the value of the tag is taken as path. The value taken
 * from the environment variable can contain several paths. They are separated
 * by the separator specified for the platform. For instance on windows the
 * separator {@code ;} is used and on Unix the separator {@code :} is used.
 * </p>
 * <p>
 * To find a resource its type is used to find the appropriate parameters for
 * the search. If the sub-configuration with the name of the type exists then
 * this sub-configuration is used. For instance if the resource {@code tex}
 * with the type {@code fmt} is sought then the sub-configuration {@code fmt}
 * determines how to find this file.
 * </p>
 * <p>
 * If no sub-configuration of the given type is present then the attribute
 * {@code default} is used to find the default sub-configuration. In the
 * example given above this default configuration is called {@code default}.
 * Nevertheless it would also be possible to point the default configuration to
 * another existing configuration. The attribute {@code default} is mandatory.
 * </p>
 * <p>
 * Each sub-configuration takes the {@code extension} in arbitrary number.
 * {@code extension} contains the extension appended after the resource name.
 * </p>
 * <p>
 * All combinations of resource name and extension are tried in turn to be found
 * in the file database. If one combination leads to a readable file then an
 * input stream to this file is used.
 * </p>
 * <p>
 * The attribute {@code trace} can be used to force a tracing of the actions in
 * the log file. The tracing is performed only if a logger is present when
 * needed. The tracing flag can be overwritten at run-time. The attribute
 * {@code trace} is optional.
 * </p>
 * <p>
 * The attribute {@code capacity} can be used to configure the initial capacity
 * of the internal cache for the file database. If this number is less than one
 * than an internal default is used. This value should be larger than the number
 * of files expected for best performance. The attribute {@code capacity} is
 * optional.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class LsrFinder extends AbstractFinder
    implements
    PropertyAware,
    EnvironmentAware {

  /**
   * The field {@code ATTR_PROPERTY} contains the attribute name for the
   * property access.
   */
  private static final String ATTR_PROPERTY = "property";

  /**
   * The field {@code INITIAL_LIST_SIZE} contains the initial size of the
   * collision list items in the cache.
   */
  private static final int INITIAL_LIST_SIZE = 3;

  /**
   * The field {@code LSR_FILE_NAME} contains the name of the ls-R file.
   */
  private static final String LSR_FILE_NAME = "ls-R";

  /**
   * The field {@code TAG_PATH} contains the name of the tag to identify
   * paths.
   */
  private static final String TAG_PATH = "path";

  /**
   * The field {@code cache} contains the map for the ls-R entries.
   */
  private Map<String, Object> cache = null;

  /**
   * The field {@code initialCapacity} contains the initial capacity of the
   * cache. If the value is less than 1 then the default of the underling
   * implementation is used.
   */
  private int initialCapacity = -1;

  /**
   * The field {@code properties} contains the properties provided for this
   * finder.
   */
  private Properties properties = System.getProperties();

  /**
   * The field {@code environment} contains the environment.
   */
  private Map<String, String> environment = System.getenv();

  /**
   * Creates a new object.
   *
   * @param configuration the encapsulated configuration object
   * @throws ConfigurationMissingException in case of an error
   */
  public LsrFinder( Configuration configuration )
      throws ConfigurationMissingException {

    super( configuration );

    String a = configuration.getAttribute( "capacity" );
    if( a != null && !"".equals( a ) ) {
      try {
        this.initialCapacity = Integer.parseInt( a );
      } catch( NumberFormatException e ) {
        this.initialCapacity = -1;
      }
    }
  }

  /**
   * java.lang.String)
   */
  @SuppressWarnings("unchecked")
  public NamedInputStream findResource( String name, String type )
      throws ConfigurationException {

    trace( "Searching", name, type, null );

    if( cache == null ) {
      initialize();
    }

    Configuration config = getConfiguration();
    Configuration cfg = config.findConfiguration( type );
    if( cfg == null ) {
      String t = config.getAttribute( ATTR_DEFAULT );
      if( t == null ) {
        throw new ConfigurationMissingAttributeException( ATTR_DEFAULT,
                                                          config );
      }
      cfg = config.getConfiguration( t );
      if( cfg == null ) {
        trace( "DefaultNotFound", type, t, null );
        return null;
      }
      trace( "ConfigurationNotFound", type, t, null );
    }
    String t = config.getAttribute( "skip" );
    if( t != null && Boolean.valueOf( t ).booleanValue() ) {

      trace( "Skipped", type, null, null );
      return null;
    }

    for( String ext : cfg.getValues( EXTENSION_TAG ) ) {

      Object c = cache.get( name + ext );
      if( c == null ) {
        continue;
      }
      else if( c instanceof File ) {
        File file = (File) c;
        trace( "Try", file.toString(), null, null );
        if( file.canRead() ) {
          try {
            InputStream stream = new FileInputStream( file );
            trace( "Found", file.toString(), null, null );
            return new NamedInputStream( stream, file.toString() );
          } catch( FileNotFoundException e ) {
            // ignore unreadable files
            trace( "FoundUnreadable", file.toString(), null, null );
            continue;
          }
        }
      }
      else {

        for( File file : (List<File>) c ) {
          if( file != null ) {
            trace( "Try", file.toString(), null, null );
            if( !file.canRead() ) {
              try {
                InputStream stream = new FileInputStream( file );
                trace( "Found", file.toString(), null, null );
                return new NamedInputStream( stream,
                                             file.toString() );
              } catch( FileNotFoundException e ) {
                // ignore unreadable files
                // this should not happen since it has been
                // checked before
                trace( "FoundUnreadable", file.toString(), null,
                       null );
                continue;
              }
            }
          }
        }
      }
    }
    trace( "Failed", name, null, null );
    return null;
  }

  /**
   * Load the external cache file into memory.
   *
   * @throws ConfigurationException in case of an error
   */
  private void initialize() throws ConfigurationException {

    Configuration config = getConfiguration();
    Iterator<Configuration> it = config.iterator( TAG_PATH );
    if( !it.hasNext() ) {
      throw new ConfigurationMissingException( TAG_PATH, config.toString() );
    }

    cache =
        (initialCapacity > 0 ? new HashMap<String, Object>(
            initialCapacity ) : new HashMap<String, Object>());

    while( it.hasNext() ) {
      Configuration cfg = it.next();
      String pathProperty = cfg.getAttribute( ATTR_PROPERTY );
      String path = null;
      if( pathProperty != null ) {
        path = properties.getProperty( pathProperty );
        if( path == null ) {
          trace( "UndefinedProperty", pathProperty, null, null );
          continue;
        }
      }
      else {
        String env = cfg.getAttribute( "env" );
        if( env != null ) {
          path = environment.get( env );
          if( path == null ) {
            trace( "UndefinedEnv", env, null );
            continue;
          }
        }
      }
      if( path != null ) {
        for( String s : path.split( System.getProperty( "path.separator",
                                                        ":" ) ) ) {
          load( s );
        }
      }
      else {
        path = cfg.getValue();
        if( path != null && !path.equals( "" ) ) {
          load( path );
        }
      }
    }
  }

  /**
   * Load the ls-R file.
   *
   * @param path the path for the ls-R file
   * @throws ConfigurationException if an error occurred
   */
  @SuppressWarnings("unchecked")
  private void load( String path ) throws ConfigurationException {

    long start = System.currentTimeMillis();
    File file = new File( path, LSR_FILE_NAME );
    if( !file.canRead() ) {
      trace( "UnreadableLsr", file.toString(), null, null );
      return;
    }

    File directory = new File( path );

    try {
      BufferedInputStream in =
          new BufferedInputStream( new FileInputStream( file ) );
      for( int c = in.read(); c >= 0; c = in.read() ) {
        if( c == '%' ) {
          do {
            c = in.read();
          } while( c >= 0 && c != '\r' && c != '\n' );
        }
        else if( c >= ' ' ) {
          StringBuilder line = new StringBuilder();
          do {
            line.append( (char) c );
            c = in.read();
          } while( c >= 0 && c != '\r' && c != '\n' );
          int len = line.length();
          if( len != 0 ) {
            if( line.charAt( len - 1 ) == ':' ) {
              line.deleteCharAt( len - 1 );
              if( len > 2 && line.charAt( 0 ) == '.'
                  && line.charAt( 1 ) == '/' ) {
                line.delete( 0, 2 );
              }
              directory = new File( path, line.toString() );
            }
            else {
              String key = line.toString();
              Object cc = cache.get( key );
              File value = new File( directory, line.toString() );
              if( cc == null ) {
                cache.put( key, value );
              }
              else if( cc instanceof File ) {
                List<Object> list =
                    new ArrayList<Object>( INITIAL_LIST_SIZE );
                cache.put( key, list );
                list.add( cc );
                list.add( value );
              }
              else {
                List<File> list = (List<File>) cc;
                list.add( value );
              }
            }
          }
        }
      }

      // BufferedReader in = new BufferedReader(new FileReader(file));
      // int len;

      // for (String line = in.readLine(); line != null; line = in
      // .readLine()) {
      // len = line.length();
      // if (len == 0 || line.charAt(0) == '%') {
      // continue;
      // } else if (line.charAt(len - 1) == ':') {
      // directory = new File(path, 
      // line.substring((line.startsWith("./") ? 2 : 0),
      // len - 1));
      // } else {
      // Object c = cache.get(line);
      // if (c == null) {
      // cache.put(line, new File(directory, line));
      // } else if (c instanceof File) {
      // List list = new ArrayList(INITIAL_LIST_SIZE);
      // list.add(c);
      // list.add(new File(directory, line));
      // cache.put(line, list);
      // } else {
      // List list = (List) c;
      // list.add(new File(directory, line));
      // }
      // }
      // }
      in.close();

    } catch( FileNotFoundException e ) {
      throw new ConfigurationWrapperException( e );
    } catch( IOException e ) {
      throw new ConfigurationIOException( e );
    }

    trace( "DatabaseLoaded", file.toString(),
           Long.toString( System.currentTimeMillis() - start ),
           Integer.toString( cache.size() ) );
    // PrintStream err = System.err;
    // err.print(file);
    // err.print('\t');
    // err.println(System.currentTimeMillis() - start);
  }

  /**
   * Setter for the environment. The default for the environment is the system
   * environment.
   *
   * @param environment the environment
   */
  public void setEnvironment( Map<String, String> environment ) {

    this.environment = environment;
  }

  /**
   * Setter for the properties.
   *
   * @param prop the new properties
   * @see org.extex.resource.PropertyAware#setProperties(java.util.Properties)
   */
  public void setProperties( Properties prop ) {

    properties = prop;
  }

}
