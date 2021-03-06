/*
 * Copyright (C) 2007-2010 The ExTeX Group and individual authors listed below
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
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.io.NamedInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This resource finder utilizes the Java class finder to search in the class
 * path for index files and resolve a resource via those. Thus it is possible to
 * find resources in the file system or inside a jar archive.
 *
 * <p>
 * If a file is not found in any index then the resource is reported as not
 * found. The same holds if the file can be resolved with the index but the
 * resource does not exist.
 * </p>
 * <p>
 * The sequence in which the different indexes on the class path are consulted
 * is unspecified.
 * </p>
 *
 * <h2>Configuration</h2> The resource finder can be configured to influence its
 * actions. The following example shows a configuration for a resource finder:
 *
 * <pre>
 * &lt;Finder class="org.extex.util.resource.ClasspathArchiveFinder"
 *         trace="false"
 *         toc="META-INF/toc.index"
 *         default="default"&gt;
 *   &lt;tex&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.tex&lt;/extension&gt;
 *   &lt;/tex&gt;
 *   &lt;fmt&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *     &lt;extension&gt;.fmt&lt;/extension&gt;
 *   &lt;/fmt&gt;
 *   &lt;default&gt;
 *     &lt;extension&gt;&lt;/extension&gt;
 *   &lt;/default&gt;
 * &lt;/Finder&gt;
 * </pre>
 *
 * <p>
 * Whenever a resource is sought its type is used to find the appropriate
 * parameters for the search. If the sub-configuration with the name of the type
 * exists then this sub-configuration is used. For instance if the resource
 * {@code tex} with the type {@code fmt} is sought then the sub-configuration
 * {@code fmt} determines how to find this file.
 * </p>
 * <p>
 * If no sub-configuration of the given type is present then the attribute
 * {@code default} is used to find the default sub-configuration. In the
 * example given above this default configuration is called {@code default}.
 * Nevertheless it would also be possible to point the default configuration to
 * another existing configuration. The attribute {@code default} is mandatory.
 * </p>
 * <p>
 * Each sub-configuration takes the tag {@code extension} in arbitrary number.
 * {@code extension} contains the extension appended after the resource name.
 * </p>
 * <p>
 * All combinations of resource name and extension are tried in turn. If one
 * combination leads to a readable input stream then it is used.
 * </p>
 * <p>
 * The attribute {@code trace} can be used to force a tracing of the actions in
 * the log file. The tracing is performed only if a logger is present when
 * needed. The tracing flag can be overwritten at run-time. The attribute
 * {@code trace} is optional.
 * </p>
 * <p>
 * The attribute {@code toc} determines the file to be used as index. The
 * default is {@code toc.index}. The attribute {@code toc} is optional. The
 * contents of the index file is described below.
 * </p>
 *
 * <h2>Indexing</h2>
 *
 * <p>
 * To speed up the access to resources an index is used by this resource finder.
 * This index file contains a list of files in the form of a properties file
 * mapping file names to file names with full paths:
 * </p>
 *
 * <pre>
 * # This is a comment
 * file1.tex=texmf/data/file1.tex
 * file2.tex=texmf/file2.tex
 * </pre>
 *
 * <p>
 * The example above shows such a file. The file names and the path are
 * separated by an equals sign. The hash mark acts as comment character if it
 * occurs as first non white-space.
 * </p>
 * <p>
 * The path contained in the index is taken relative to the index. This means
 * that you have to be careful to adjust the path if it is not in the root
 * directory.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ClasspathArchiveFinder extends AbstractFinder {

  /**
   * The constant {@code DEFAULT_TOC_NAME} contains the default name of the
   * index.
   */
  private static final String DEFAULT_TOC_NAME = "META-INF/toc.index";

  /**
   * The field {@code index} contains the mapping from file names to.
   */
  private Map<String, URL> index = null;

  /**
   * Creates a new object.
   *
   * @param configuration the encapsulated configuration object
   * @throws ConfigurationException in case of an error
   */
  public ClasspathArchiveFinder( Configuration configuration )
      throws ConfigurationException {

    super( configuration );
  }

  /**
   * Find a resource which can be used for reading. If the search fails then
   * {@code null} is returned.
   *
   * @param name the base name of the resource
   * @param type the type, i.e. the extension
   * @return the file or {@code null} if none could be found
   * @throws ConfigurationException in case of an exception
   * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
   * java.lang.String)
   */
  public NamedInputStream findResource( String name, String type )
      throws ConfigurationException {

    trace( "Searching", name, type );

    if( index == null ) {
      String tocName = getConfiguration().getAttribute( "toc" );
      index = initialize( tocName != null ? tocName : DEFAULT_TOC_NAME );
    }

    Configuration configuration = getConfiguration();
    Configuration cfg = configuration.findConfiguration( type );
    if( cfg == null ) {
      String t = configuration.getAttribute( ATTR_DEFAULT );
      if( t == null ) {
        throw new ConfigurationMissingAttributeException( ATTR_DEFAULT,
                                                          configuration );
      }
      cfg = configuration.getConfiguration( t );
      if( cfg == null ) {
        return null;
      }
    }
    String t = cfg.getAttribute( ATTR_SKIP );
    if( t != null && Boolean.valueOf( t ).booleanValue() ) {

      trace( "Skipped", type, null );
      return null;
    }
    String prefix = cfg.getAttribute( "prefix" );
    if( prefix == null ) {
      prefix = "";
    }

    for( String ext : cfg.getValues( EXTENSION_TAG ) ) {

      String fullName = prefix + name + ext;
      fullName = fullName.replaceAll( "\\{type\\}", type );
      trace( "Try", fullName, null );
      URL url = index.get( fullName );
      if( url != null ) {
        InputStream stream;
        try {
          stream = url.openStream();
          if( stream != null ) {
            trace( "Found", fullName, null );
            return new NamedInputStream( stream, url.toString() );
          }
        } catch( FileNotFoundException e ) {
          trace( "NotExists", fullName, null );
        } catch( IOException e ) {
          throw new ConfigurationWrapperException( e );
        }
      }
      trace( "NotFound", fullName, null );
    }
    return null;
  }

  /**
   * Initialize the index for searching.
   *
   * @param tocName the name of the toc file
   * @return the cache
   * @throws ConfigurationWrapperException in case of a configuration problem
   */
  protected Map<String, URL> initialize( String tocName )
      throws ConfigurationWrapperException {

    long start = System.currentTimeMillis();
    Map<String, URL> cache = new HashMap<String, URL>();

    ClassLoader classLoader = getClass().getClassLoader();

    try {
      Enumeration<URL> toc = classLoader.getResources( tocName );
      while( toc.hasMoreElements() ) {
        URL url = toc.nextElement();
        trace( "IndexFound", url.toString(), "" );
        String protocol = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();
        String p = url.getPath();
        int i = p.lastIndexOf( '/' );
        if( i < 0 ) {
          continue;
        }
        p = p.substring( 0, i + 1 );

        Properties properties = new Properties();
        InputStream stream = url.openStream();
        properties.load( stream );
        stream.close();

        Enumeration<Object> keys = properties.keys();
        while( keys.hasMoreElements() ) {
          String k = keys.nextElement().toString();
          cache.put( k, new URL( protocol, host, port,
                                 p + properties.getProperty( k ) ) );
        }
      }
    } catch( IOException e ) {
      throw new ConfigurationWrapperException( e );
    }
    trace( "DatabaseLoaded",
           Long.toString( System.currentTimeMillis() - start ),
           Integer.toString( cache.size() ) );
    return cache;
  }

}
