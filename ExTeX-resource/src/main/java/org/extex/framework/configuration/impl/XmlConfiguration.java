/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides means to deal with configurations stored as XML files.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class XmlConfiguration implements Configuration {

  /**
   * This inner class provides an iterator for all sub-configurations of a
   * given node.
   */
  private class ConfigIterator implements Iterator<Configuration> {

    /**
     * The field {@code node} contains the current node.
     */
    private Node node;

    /**
     * Creates a new object.
     *
     * @param node the root node
     */
    protected ConfigIterator( Node node ) {

      this.node = node;
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other
     * words, returns {@code true} if {@code next} would return an element
     * rather than throwing an exception.)
     *
     * @return {@code true} if the iterator has more elements.
     */
    @Override
    public boolean hasNext() {

      if( node == null ) {
        return false;
      }
      for( ; ; ) {
        node = node.getNextSibling();
        if( node == null ) {
          return false;
        }
        else if( node.getNodeType() == Node.ELEMENT_NODE ) {
          return true;
        }
      }
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     * @see java.util.Iterator#next()
     */
    @Override
    public Configuration next() {

      if( node == null ) {
        return null;
      }
      while( node.getNodeType() != Node.ELEMENT_NODE ) {
        node = node.getNextSibling();
        if( node == null ) {
          return null;
        }
      }

      return new XmlConfiguration( (Element) node, base, resource );
    }

    /**
     * This is an unsupported operation and leads to an exception.
     *
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {

      throw new UnsupportedOperationException();
    }

  }

  /**
   * The field {@code ext} contains extensions to use when searching for
   * configuration files.
   */
  private static final String[] EXTENSIONS = {".xml", ""};

  /**
   * The field {@code path} contains the path to use when searching for
   * configuration files.
   */
  private static final String[] PATHS = {"config/", ""};

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2010L;

  /**
   * Recursively collect the Xpath from the root to the given node.
   *
   * @param sb   the output string buffer
   * @param node the node to start with
   */
  private static void toString( StringBuilder sb, Node node ) {

    if( node == null ) {
      return;
    }
    Node p = node.getParentNode();
    if( p != null && !(p instanceof Document) ) {
      toString( sb, p );
    }
    sb.append( '/' );
    sb.append( node.getNodeName() );
  }

  /**
   * The field {@code loader} contains the optional loader.
   */
  // private ConfigurationLoader loader = null;
  /**
   * The field {@code base} contains the base of the resource name; i.e. the
   * resource up to the last slash or the empty string if no slash is
   * contained.
   */
  private String base;

  /**
   * The field {@code fullName} contains the URL of the resource found.
   */
  private String fullName;

  /**
   * The field {@code resource} contains the name of the resource.
   */
  private String resource;

  /**
   * The field {@code root} contains the root element for this configuration.
   */
  private Element root;

  /**
   * Creates a new object with a given root element. This constructor is
   * private since it is meant for internal purposes only.
   *
   * @param root     the new root element
   * @param base     the base for the resource
   * @param resource the name of the resource
   */
  private XmlConfiguration( Element root, String base, String resource ) {

    this.root = root;
    this.base = base;
    this.resource = resource;
  }

  /**
   * Creates a new object and fills it with the configuration read from an
   * input stream.
   * <p>
   * The path given is the location of the XML resource (file) containing the
   * configuration information. This path is used to determine the XML
   * resource utilizing the class loader for this class. Thus it is possible
   * to place the XML file into a jar archive.
   * </p>
   * <p>
   * Beside of the class loader a search is performed by appending
   * {@code .xml} and/or prepending {@code config/} if the path is not
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
   * @throws ConfigurationIOException              in case of an IO exception
   * while reading
   *                                               the resource.
   */
  public XmlConfiguration( InputStream stream, String resource )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    readConfiguration( stream, resource, "" );
  }

  /**
   * The path given is the location of the XML file containing the
   * configuration information. This path is used to determine the XML file
   * utilizing the class loader for this class. Thus it is possible to place
   * the XML file into a jar archive.
   * <p>
   * Beside of the class loader a search is performed by appending
   * {@code .xml} and/or prepending {@code config/} if the path is not
   * sufficient to find the resource.
   * </p>
   * <p>
   * Example
   * <p>
   * Consider the following creation of an instance of this class
   * </p>
   *
   * <pre>
   *   cfg = new XmlConfiguration("cfg");
   *  </pre>
   * <p>
   * Then the following files are searched on the classpath until one is
   * found:
   *
   * <pre>
   *     cfg   cfg.xml   config/cfg   config/cfg.xml
   *  </pre>
   *
   * @param resource the name of the resource to be used; i.e. the file name
   * @throws ConfigurationInvalidResourceException in case that the given
   *                                               resource name is {@code
   *                                               null} or empty
   * @throws ConfigurationNotFoundException        in case that the named
   * path does
   *                                               not lead to a resource
   * @throws ConfigurationSyntaxException          in case that the resource
   * contains
   *                                               syntax errors
   * @throws ConfigurationIOException              in case of an IO exception
   * while reading
   *                                               the resource
   */
  public XmlConfiguration( String resource )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    if( resource == null || resource.equals( "" ) ) {
      throw new ConfigurationInvalidResourceException();
    }

    final int i = resource.lastIndexOf( "/" );
    final String base = i < 0 ? "" : resource.substring( 0, i + 1 );

    readConfiguration(
        locateConfiguration( resource, getClass().getClassLoader() ),
        resource,
        base );
  }

  /**
   * Extract a sub-configuration with a given name.
   * <p>
   * Consider the following example with the configuration currently rooted at
   * cfg:
   * </p>
   *
   * <pre>
   *  &lt;cfg&gt;
   *    . . .
   *    &lt;abc&gt;
   *      . . .
   *    &lt;/abc&gt;
   *    . . .
   *  &lt;/cfg&gt;
   * </pre>
   *
   * <p>
   * Then {@code findConfiguration("abc")} returns a new XMLConfig rooted at
   * abc.
   * </p>
   * <p>
   * If there are more than one tags with the same name then the first one is
   * used.
   * </p>
   * <p>
   * If there are no tags with the given name then an exception is thrown.
   * </p>
   *
   * @param name the tag name of the sub-configuration
   * @return the sub-configuration or {@code null} if none was found
   * @throws ConfigurationInvalidResourceException in case that the given
   *                                               resource name is {@code
   *                                               null} or empty
   * @throws ConfigurationNotFoundException        in case that the named
   * path does
   *                                               not lead to a resource
   * @throws ConfigurationSyntaxException          in case that the resource
   * contains
   *                                               syntax errors
   * @throws ConfigurationIOException              in case of an IO exception
   * while reading
   *                                               the resource
   * @see #getConfiguration(java.lang.String)
   * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
   */
  @Override
  public Configuration findConfiguration( String name )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    for( Node node = root.getFirstChild(); node != null; node =
        node.getNextSibling() ) {
      if( node.getNodeName().equals( name ) ) {

        String src = ((Element) node).getAttribute( "src" );

        if( src == null || src.equals( "" ) ) {
          return new XmlConfiguration( (Element) node, base, resource );
        }

        return new XmlConfiguration( base + src ).src( name, node );
      }
    }

    return null;
  }

  /**
   * Extract a sub-configuration with a given name and a given attribute.
   * <p>
   * Consider the following example with the configuration currently rooted at
   * cfg:
   * </p>
   *
   * <pre>
   *   &lt;cfg&gt;
   *     . . .
   *     &lt;abc name="one"&gt;
   *     . . .
   *     &lt;/abc&gt;
   *     &lt;abc name="two"&gt;
   *     . . .
   *     &lt;/abc&gt;
   *     . . .
   *   &lt;/cfg&gt;
   * </pre>
   *
   * <p>
   * Then {@code getConfig("abc","two")} returns a new XMLConfig rooted at
   * the abc with the name attribute "two".
   * </p>
   * <p>
   * If there are more than one tags with the same name then the first one is
   * used.
   * </p>
   * <p>
   * If there are no tags with the given name then {@code null} is
   * returned.
   * </p>
   *
   * @param key       the tag name of the sub-configuration
   * @param attribute the value of the attribute name
   * @return the sub-configuration
   * @throws ConfigurationException in case of other errors.
   */
  @Override
  public Configuration findConfiguration( String key, String attribute )
      throws ConfigurationException {

    if( key == null || attribute == null ) {
      return null;
    }

    for( Node node = root.getFirstChild(); node != null; node =
        node.getNextSibling() ) {
      if( key.equals( node.getNodeName() )
          && attribute.equals( ((Element) node).getAttribute( "name" ) ) ) {
        return new XmlConfiguration( (Element) node, base, resource );
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.framework.configuration.Configuration#getAttribute(java.lang.String)
   */
  @Override
  public String getAttribute( String name ) {

    return (this.root.getAttributeNode( name ) == null ? null :
        this.root.getAttribute( name ));
  }

  /**
   * Extract a sub-configuration with a given name.
   * <p>
   * Consider the following example with the configuration currently rooted at
   * cfg:
   * </p>
   *
   * <pre>
   *  &lt;cfg&gt;
   *    . . .
   *    &lt;abc&gt;
   *      . . .
   *    &lt;/abc&gt;
   *    . . .
   *  &lt;/cfg&gt;
   * </pre>
   *
   * <p>
   * Then {@code getConfiguration("abc")} returns a new XMLConfig rooted at
   * abc.
   * </p>
   * <p>
   * If there are more than one tags with the same name then the first one is
   * used.
   * </p>
   * <p>
   * If there are no tags with the given name then an exception is thrown.
   * </p>
   *
   * @param name the tag name of the sub-configuration
   * @return the sub-configuration
   * @throws ConfigurationNotFoundException        in case that the
   * configuration
   *                                               does not exist.
   * @throws ConfigurationIOException              in case that an
   * IOException occurred
   *                                               while reading the
   *                                               configuration.
   * @throws ConfigurationSyntaxException          in case of a syntax error
   * in the
   *                                               configuration.
   * @throws ConfigurationInvalidResourceException in case that the given
   *                                               resource name is {@code
   *                                               null} or empty
   * @see #findConfiguration(String)
   */
  @Override
  public Configuration getConfiguration( String name )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    Configuration cfg = findConfiguration( name );

    if( cfg == null ) {
      throw new ConfigurationNotFoundException( name, toString() );
    }
    return cfg;
  }

  /**
   * Extract a sub-configuration with a given name and a given attribute.
   * <p>
   * Consider the following example with the configuration currently rooted at
   * cfg:
   * </p>
   *
   * <pre>
   *  &lt;cfg&gt;
   *    . . .
   *    &lt;abc name="one"&gt;
   *      . . .
   *    &lt;/abc&gt;
   *    &lt;abc name="two"&gt;
   *      . . .
   *    &lt;/abc&gt;
   *    . . .
   *  &lt;/cfg&gt;
   * </pre>
   *
   * <p>
   * Then {@code getConfig("abc","two")} returns a new XMLConfig rooted at
   * the abc with the name attribute "two".
   * </p>
   * <p>
   * If there are more than one tags with the same name then the first one is
   * used.
   * </p>
   * <p>
   * If there are no tags with the given name then an exception is thrown.
   * </p>
   *
   * @param key       the tag name of the sub-configuration
   * @param attribute the value of the attribute name
   * @return the sub-configuration
   * @throws ConfigurationNotFoundException in case that the given name does
   *                                        not correspond to one of the tags
   *                                        in the current configuration
   * @throws ConfigurationException         in case of some other kind of error
   */
  @Override
  public Configuration getConfiguration( String key, String attribute )
      throws ConfigurationNotFoundException,
      ConfigurationException {

    Configuration cfg = findConfiguration( key, attribute );

    if( cfg == null ) {
      throw new ConfigurationNotFoundException( null,
                                                key + "[name=" + attribute +
                                                    "]" );
    }
    return cfg;
  }

  /**
   * Get the accumulated text values of a node.
   *
   * @param node the node
   * @return the accumulated text value
   */
  private String getNodeValue( Node node ) {

    StringBuilder sb = new StringBuilder();

    for( Node n = node.getFirstChild(); n != null; n = n.getNextSibling() ) {
      if( n.getNodeType() == Node.TEXT_NODE ) {
        sb.append( n.getNodeValue() );
      }
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.framework.configuration.Configuration#getValue()
   */
  @Override
  public String getValue() throws ConfigurationException {

    return getNodeValue( root );
  }

  /**
   * Get the text value of the first tag with a given name in the
   * configuration. If none is found then the empty string is returned.
   * <p>
   * Consider the following example with the configuration currently rooted at
   * cfg:
   * </p>
   *
   * <pre>
   *   &lt;cfg&gt;
   *     . . .
   *     &lt;one&gt;the first value&lt;/one&gt;
   *     &lt;two&gt;the second value&lt;/two&gt;
   *     . . .
   *   &lt;/cfg&gt;
   * </pre>
   *
   * <p>
   * Then {@code getValue("two")} returns the String "the second value".
   * </p>
   *
   * @param tag the name of the tag
   * @return the value of the tag or the empty string
   */
  @Override
  public String getValue( String tag ) {

    for( Node node = root.getFirstChild(); node != null; node =
        node.getNextSibling() ) {
      if( node.getNodeName().equals( tag ) ) {
        return getNodeValue( node );
      }
    }

    return "";
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.framework.configuration.Configuration#getValueAsInteger(java.lang.String,
   * int)
   */
  @Override
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
   * Get the list of all values with the given tag name in the current
   * configuration and append them to a given StringList.
   * <p>
   * {@inheritDoc}
   *
   * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
   * java.lang.String)
   */
  @Override
  public void getValues( List<String> list, String key ) {

    if( list == null ) {
      throw new IllegalArgumentException( "list" );
    }

    for( Node node = root.getFirstChild(); node != null; node =
        node.getNextSibling() ) {
      if( node.getNodeName().equals( key ) ) {
        list.add( getNodeValue( node ) );
      }
    }
  }

  /**
   * Get the list of all values with the given tag name in the current
   * configuration.
   *
   * @param tag the name of the tags
   * @return the list of values
   * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
   */
  @Override
  public List<String> getValues( String tag ) {

    List<String> result = new ArrayList<>();
    getValues( result, tag );
    return result;
  }

  /**
   * Get an iterator for all sub-configurations.
   *
   * @return an iterator for all sub-configurations
   * @see org.extex.framework.configuration.Configuration#iterator()
   */
  @Override
  public Iterator<Configuration> iterator() {

    return new ConfigIterator( root.getFirstChild() );
  }

  /**
   * Retrieve an iterator over all items of a sub-configuration.
   *
   * @param key the name of the sub-configuration
   * @return the iterator
   * @throws ConfigurationIOException              in case that an IO
   * exception occurs
   *                                               during the reading of the
   *                                               configuration.
   * @throws ConfigurationSyntaxException          in case that the
   * configuration
   *                                               contains a syntax error.
   * @throws ConfigurationNotFoundException        in case that the specified
   *                                               configuration can not be
   *                                               found.
   * @throws ConfigurationInvalidResourceException in case that the resource
   *                                               is invalid
   * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
   */
  @Override
  public Iterator<Configuration> iterator( String key )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    final List<Configuration> list = new ArrayList<>();

    for( Node node = root.getFirstChild(); node != null; node =
        node.getNextSibling() ) {
      if( node.getNodeName().equals( key ) ) {
        String src = ((Element) node).getAttribute( "src" );
        if( src == null || src.equals( "" ) ) {
          list.add( new XmlConfiguration( (Element) node, base,
                                          resource ) );
        }
        else {
          list.add( new XmlConfiguration( base + src ).src( key, node ) );
        }
      }
    }

    return list.iterator();
  }

  /**
   * Search for a configuration file taking into account a list of prefixes
   * (path) and postfixes (ext).
   *
   * @param name        the base name of the configuration to find. The
   *                    path elements
   *                    and extensions are attached in turn to build the
   *                    complete name.
   * @param classLoader the class loader to use for finding the resource
   * @return an input stream to the requested configuration or
   * {@code null} if none could be opened.
   */
  private InputStream locateConfiguration( String name,
                                           ClassLoader classLoader ) {
    for( final String p : PATHS ) {
      for( final String ext : EXTENSIONS ) {
        fullName = p + name + ext;

        final InputStream stream =
            classLoader.getResourceAsStream( fullName );
        if( stream != null ) {
          return stream;
        }
      }
    }

    return null;
  }

  /**
   * Read the configuration from a stream.
   *
   * @param stream      the stream to read the configuration from.
   * @param theResource the name of the resource to be used; i.e. something
   *                    like the file name
   * @param theBase     the new value for base
   * @throws ConfigurationNotFoundException in case that the configuration
   *                                        could not be found
   * @throws ConfigurationIOException       in case of an IO error during
   * reading
   * @throws ConfigurationSyntaxException   in case of a syntax error in the
   *                                        configuration XML
   */
  protected void readConfiguration(
      InputStream stream, String theResource, String theBase )
      throws ConfigurationNotFoundException, ConfigurationIOException,
      ConfigurationSyntaxException {

    if( stream == null ) {
      throw new ConfigurationNotFoundException( theResource, null );
    }
    this.resource = theResource;
    this.base = theBase;

    try {
      DocumentBuilder builder =
          DocumentBuilderFactory.newInstance().newDocumentBuilder();
      root = builder.parse( stream ).getDocumentElement();
    } catch( IOException e ) {
      throw new ConfigurationIOException( e );
    } catch( ParserConfigurationException | FactoryConfigurationError e ) {
      throw new ConfigurationSyntaxException( e.getLocalizedMessage(),
                                              theResource );
    } catch( SAXException e ) {
      throw new ConfigurationSyntaxException( e.getLocalizedMessage(),
                                              fullName != null ? fullName :
                                                  theResource );
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)
   */
  @Override
  public void setConfigurationLoader( ConfigurationLoader loader ) {

    // this.loader = loader;
  }

  /**
   * Recursively follow the src attribute if present.
   *
   * @param name the name of the current tag
   * @param node the current DOM node
   * @return the configuration
   * @throws ConfigurationInvalidResourceException in case of an invalid
   *                                               resource
   * @throws ConfigurationNotFoundException        in case of a missing
   * configuration
   * @throws ConfigurationSyntaxException          in case of an syntax error
   * @throws ConfigurationIOException              in case of an IO error
   */
  protected Configuration src( String name, Node node )
      throws ConfigurationInvalidResourceException,
      ConfigurationNotFoundException,
      ConfigurationSyntaxException,
      ConfigurationIOException {

    String src = root.getAttribute( "src" );

    if( src == null || src.equals( "" ) ) {
      return this;
    }
    Configuration cfg = new XmlConfiguration( base + src ).src( name, root );
    if( !((XmlConfiguration) cfg).root.getNodeName().equals( name ) ) {
      throw new ConfigurationNotFoundException( name, src );
    }
    return cfg;
  }

  /**
   * Get the printable representation of this configuration. Something like an
   * XPath expression describing the configuration is produced for this
   * instance.
   *
   * @return a string representation of the object.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    if( resource != null ) {
      buffer.append( "document(\"" );
      buffer.append( resource );
      buffer.append( "\")" );
    }
    toString( buffer, root );
    return buffer.toString();
  }
}
