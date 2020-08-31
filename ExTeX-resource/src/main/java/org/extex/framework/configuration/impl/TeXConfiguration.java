/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.impl;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.text.MessageFormat;
import java.util.*;

/**
 * This configuration represents a TeX like file format.
 *
 * <pre>
 *  \config[abc={12},def={23}]{
 *   \subconfig[xyz={4365}]{
 *   }
 *  }
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TeXConfiguration implements Configuration {

  /**
   * The field {@code name} contains the name of the configuration.
   */
  private String name;

  /**
   * The field {@code resource} contains the name of the resource.
   */
  private String resource;

  /**
   * The field {@code base} contains the base of the resource name; i.e. the
   * resource up to the last slash or the empty string if no slash is
   * contained.
   */
  // private String base;
  /**
   * The field {@code attributes} contains the attributes.
   */
  private final Map<String, String> attributes = new HashMap<String, String>();

  /**
   * The field {@code configurations} contains the configurations.
   */
  private final Map<String, List<TeXConfiguration>> configurations =
      new HashMap<String, List<TeXConfiguration>>();

  /**
   * The field {@code loader} contains the loader.
   */
  // private ConfigurationLoader loader;
  /**
   * The field {@code parts} contains the parts.
   */
  private final List<Object> parts = new ArrayList<Object>();

  /**
   * Creates a new object and fills it with the configuration read from an
   * input stream.
   * <p>
   * The path given is the location of the TeX resource (file) containing the
   * configuration information. This path is used to determine the TeX
   * resource utilizing the class loader for this class. Thus it is possible
   * to place the TeX file into a jar archive.
   * </p>
   * <p>
   * Beside of the class loader a search is performed by appending
   * {@code .tex} and/or prepending {@code config/} if the path is not
   * sufficient to find the resource.
   * </p>
   *
   * @param stream   the stream to read the configuration from.
   * @param resource the name of the resource to be used; i.e. something like
   *                 the file name
   * @throws ConfigurationInvalidResourceException in case that the given
   *                                               resource name is {@code
   *                                               null} or empty.
   * @throws ConfigurationNotFoundException        in case that the named
   * path does
   *                                               not lead to a resource.
   * @throws ConfigurationSyntaxException          in case that the
   * resource contains
   *                                               syntax errors.
   * @throws ConfigurationIOException              in case of an IO
   * exception while reading
   *                                               the resource.
   */
  public TeXConfiguration( InputStream stream, String resource )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    read( stream, resource, "" );
  }

  /**
   * Creates a new object.
   *
   * @param reader the reader
   * @param name   the name of the configuration
   * @throws IOException in case of an I/O error
   */
  private TeXConfiguration( PushbackReader reader, String name )
      throws IOException {

    parse( reader, name );
  }

  public Configuration findConfiguration( String key )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    List<TeXConfiguration> list = configurations.get( key );
    return (list == null || list.size() == 0 ? null : list.get( 0 ));
  }

  /**
   * java.lang.String)
   */
  public Configuration findConfiguration( String key, String attribute )
      throws ConfigurationException {

    List<TeXConfiguration> list = configurations.get( key );
    if( list != null ) {
      for( Configuration cfg : list ) {
        String n = cfg.getAttribute( "name" );
        if( n != null && attribute.equals( n ) ) {
          return cfg;
        }
      }
    }
    return null;
  }

  /**
   * Apply a message format taken from a resource bundle to some arguments.
   *
   * @param key  the key
   * @param args the arguments
   * @return the formatted string
   */
  private String format( String key, Object... args ) {

    return MessageFormat
        .format(
            ResourceBundle.getBundle( getClass().getName() ).getString( key ),
            args );
  }

  public String getAttribute( String attribute ) {

    return attributes.get( attribute );
  }

  public Configuration getConfiguration( String key )
      throws ConfigurationException {

    Configuration cfg = findConfiguration( key );
    if( cfg == null ) {
      throw new ConfigurationNotFoundException( key, resource );
    }
    return cfg;
  }

  /**
   * java.lang.String)
   */
  public Configuration getConfiguration( String key, String attribute )
      throws ConfigurationException {

    Configuration cfg = findConfiguration( key, attribute );
    if( cfg == null ) {
      throw new ConfigurationNotFoundException( key + "[name=" + attribute
                                                    + "]", resource );
    }
    return cfg;
  }

  /**
   * Getter for the name.
   *
   * @return the name
   */
  protected String getName() {

    return name;
  }

  public String getValue() throws ConfigurationException {

    StringBuilder sb = new StringBuilder();

    for( Object obj : parts ) {
      if( obj instanceof TeXConfiguration ) {
        sb.append( ((TeXConfiguration) obj).getValue() );
      }
      else {
        sb.append( obj.toString() );
      }
    }

    return sb.toString();
  }

  public String getValue( String key ) throws ConfigurationException {

    for( Object obj : parts ) {
      if( obj instanceof TeXConfiguration
          && ((TeXConfiguration) obj).getName().equals( key ) ) {
        return ((TeXConfiguration) obj).getValue();
      }
    }

    return "";
  }

  /**
   * int)
   */
  public int getValueAsInteger( String key, int defaultValue )
      throws ConfigurationException {

    String s = getValue( key );
    if( s != null ) {
      s = s.trim();
      if( s.matches( "-?[0-9]+" ) ) {
        return Integer.parseInt( s );
      }
    }

    return defaultValue;
  }

  /**
   * java.lang.String)
   */
  public void getValues( List<String> list, String key ) {

    for( TeXConfiguration cfg : configurations.get( key ) ) {
      list.add( cfg.getValue() );
    }
  }

  public List<String> getValues( String key ) {

    List<String> result = new ArrayList<String>();
    getValues( result, key );
    return result;
  }

  public Iterator<Configuration> iterator() throws ConfigurationException {

    List<Configuration> list = new ArrayList<Configuration>();

    for( Object obj : parts ) {
      if( obj instanceof TeXConfiguration ) {
        list.add( (Configuration) obj );
      }
    }
    return list.iterator();
  }

  public Iterator<Configuration> iterator( String key )
      throws ConfigurationException {

    List<Configuration> list = new ArrayList<Configuration>();

    for( Object obj : parts ) {
      if( obj instanceof TeXConfiguration
          && ((TeXConfiguration) obj).getName().equals( key ) ) {
        list.add( (Configuration) obj );
      }
    }
    return list.iterator();
  }

  /**
   * Parse a configuration from a file.
   *
   * @param reader the reader
   * @throws IOException in case of an I/O error
   */
  private void parse( PushbackReader reader ) throws IOException {

    parse( reader, parseTag( reader ) );
  }

  /**
   * Parse a configuration from a file.
   *
   * @param reader  the reader
   * @param tagName the tag name
   * @throws IOException in case of an I/O error
   */
  private void parse( PushbackReader reader, String tagName )
      throws IOException {

    this.name = tagName;
    int c = parseChar( reader );
    if( c == '[' ) {
      parseAttributes( reader );
      c = parseChar( reader );
    }
    if( c != '{' ) {
      throw new ConfigurationSyntaxException( format( "MissingBrace" ),
                                              resource );
    }
    for( String key = parseText( reader ); !key.equals( "}" ); key =
        parseText( reader ) ) {
      TeXConfiguration cfg = new TeXConfiguration( reader, key );
      parts.add( cfg );
      List<TeXConfiguration> al = configurations.get( key );
      if( al == null ) {
        al = new ArrayList<TeXConfiguration>();
        configurations.put( key, al );
      }
      al.add( cfg );
    }
  }

  /**
   * Parse an attribute name.
   *
   * @param cc     the character already read
   * @param reader the reader
   * @return the attribute name
   * @throws IOException in case of an I/O error
   */
  private String parseAttributeName( int cc, PushbackReader reader )
      throws IOException {

    if( cc >= 0 ) {
      StringBuilder sb = new StringBuilder();
      sb.append( (char) cc );
      for( int c = reader.read(); c >= 0; c = reader.read() ) {
        if( c == '=' ) {
          return sb.toString();
        }
        else if( c == '%' || Character.isWhitespace( c ) ) {
          reader.unread( c );
          c = parseChar( reader );
          if( c != '=' ) {
            throw new ConfigurationSyntaxException(
                format( "MissingEquals" ), resource );
          }
          return sb.toString();
        }
        sb.append( (char) c );
      }
    }
    throw new ConfigurationSyntaxException( format( "EofError" ), resource );
  }

  /**
   * Parse a list of attributes.
   *
   * @param reader the reader
   * @throws IOException in case of an I/O error
   */
  private void parseAttributes( PushbackReader reader ) throws IOException {

    boolean first = true;

    for( int c = parseChar( reader ); c != ']'; c = parseChar( reader ) ) {
      if( first ) {
        first = false;
      }
      else if( c != ',' ) {
        throw new ConfigurationSyntaxException( format( "MissingComma" ),
                                                resource );
      }
      else {
        c = parseChar( reader );
      }

      String attribute = parseAttributeName( c, reader );
      if( attributes.containsKey( attribute ) ) {
        throw new ConfigurationSyntaxException( format(
            "DoubleAttribute", attribute ), resource );
      }
      String value = parseAttributeValue( reader );
      attributes.put( attribute, value );
    }
  }

  /**
   * Parse the right hand side of an attribute assignment.
   *
   * @param reader the reader
   * @return the value
   * @throws IOException in case of an I/O error
   */
  private String parseAttributeValue( PushbackReader reader )
      throws IOException {

    StringBuilder sb = new StringBuilder();
    int c = parseChar( reader );
    if( c == '{' ) {
      int depth = 1;
      for( c = reader.read(); c >= 0; c = reader.read() ) {
        if( c == '{' ) {
          depth++;
        }
        else if( c == '}' ) {
          depth--;
          if( depth <= 0 ) {
            return sb.toString();
          }
        }
        else if( c == '%' ) {
          reader.unread( c );
          c = parseChar( reader );
          reader.unread( c );
        }
        else if( c == '\\' ) {
          c = reader.read();
          if( c < 0 ) {
            break;
          }
        }
        sb.append( (char) c );
      }
    }
    else {
      do {
        if( Character.isWhitespace( c ) ) {
          return sb.toString();
        }
        else if( c == '%' ) {
          reader.unread( c );
          c = parseChar( reader );
          reader.unread( c );
        }
        else if( c == ']' || c == ',' ) {
          reader.unread( c );
          return sb.toString();
        }
        sb.append( (char) c );
        c = reader.read();
      } while( c >= 0 );
    }
    throw new ConfigurationSyntaxException( format( "EofError" ), resource );
  }

  /**
   * Find the next non-space character while ignoring comments.
   *
   * @param reader the reader
   * @return the next character
   * @throws IOException in case of an I/O error
   */
  private int parseChar( PushbackReader reader ) throws IOException {

    for( int c = reader.read(); c >= 0; c = reader.read() ) {
      if( c == '%' ) {
        do {
          c = reader.read();
        } while( c >= 0 && c != '\n' );
      }
      else if( !Character.isWhitespace( c ) ) {
        return c;
      }
    }
    throw new ConfigurationSyntaxException( format( "EofError" ), resource );
  }

  /**
   * Skip over whitespace to the next cs and parse it.
   *
   * @param reader the reader
   * @return the tag
   * @throws IOException in case of an I/O error
   */
  private String parseTag( PushbackReader reader ) throws IOException {

    int c = parseChar( reader );
    if( c != '\\' ) {
      throw new ConfigurationSyntaxException( format( "MissingTag" ),
                                              resource );
    }

    c = reader.read();
    if( c < 0 ) {
      throw new ConfigurationSyntaxException( format( "EofError" ), resource );
    }
    else if( !Character.isLetter( c ) ) {
      throw new ConfigurationSyntaxException( format( "MissingLetter" ),
                                              resource );
    }
    StringBuilder sb = new StringBuilder();
    sb.append( (char) c );

    for( c = parseChar( reader ); Character.isLetterOrDigit( c ); c =
        parseChar( reader ) ) {
      sb.append( (char) c );
    }
    reader.unread( c );
    return sb.toString();
  }

  /**
   * Parse text to a \ or a } and store it in the parts list.
   *
   * @param reader the reader
   * @return "}" or the next cs
   * @throws IOException in case of an I/O error
   */
  private String parseText( PushbackReader reader ) throws IOException {

    StringBuilder sb = new StringBuilder();

    for( int c = reader.read(); c >= 0; c = reader.read() ) {

      if( c == '}' ) {
        if( sb.length() != 0 ) {
          parts.add( sb.toString() );
        }
        return "}";
      }
      else if( c == '%' ) {
        reader.unread( c );
        c = parseChar( reader );
        reader.unread( c );
      }
      else if( c == '\\' ) {

        c = reader.read();
        if( c < 0 ) {
          break;
        }
        if( Character.isLetter( c ) ) {
          if( sb.length() != 0 ) {
            parts.add( sb.toString() );
          }
          reader.unread( c );
          reader.unread( '\\' );
          return parseTag( reader );
        }
      }

      sb.append( (char) c );
    }

    throw new ConfigurationSyntaxException( format( "EofError" ), resource );
  }

  /**
   * Read the configuration from a stream.
   *
   * @param stream       the stream to read the configuration from.
   * @param resourceName the name of the resource to be used; i.e. something
   *                     like the file name
   * @param base         the new value for base
   * @throws ConfigurationNotFoundException in case that the configuration
   *                                        could not be found
   * @throws ConfigurationIOException       in case of an IO error during
   * reading
   * @throws ConfigurationSyntaxException   in case of a syntax error in the
   *                                        configuration XML
   */
  protected void read( InputStream stream, String resourceName, String base )
      throws ConfigurationNotFoundException,
      ConfigurationIOException,
      ConfigurationSyntaxException {

    if( stream == null ) {
      throw new ConfigurationNotFoundException( resourceName, null );
    }
    this.resource = resourceName;
    // this.base = base;

    PushbackReader reader =
        new PushbackReader( new InputStreamReader( stream ), 2 );
    try {
      try {
        parse( reader );
      } finally {
        reader.close();
      }
    } catch( IOException e ) {
      throw new ConfigurationIOException( e );
    }
  }

  public void setConfigurationLoader( ConfigurationLoader loader ) {

    // this.loader = loader;
  }

}
