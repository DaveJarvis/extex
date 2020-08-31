/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link BibReaderFactory}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BibReaderFactoryTest {

  /**
   * The field {@code CFG} contains the test configuration.
   */
  private static final Configuration CFG = new Configuration() {

    @Override
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return this;
    }

    /**
     *      java.lang.String)
     */
    @Override
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return this;
    }

    @Override
    public String getAttribute( String name ) {

      if( "base".equals( name ) ) {
        return null;
      }
      if( "encoding".equals( name ) ) {
        return null;
      }
      assertTrue( name, "class".equals( name ) );
      return BibReader099Impl.class.getName();
    }

    @Override
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return this;
    }

    /**
     *      java.lang.String)
     */
    @Override
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    @Override
    public String getValue() throws ConfigurationException {

      return null;
    }

    @Override
    public String getValue( String key ) throws ConfigurationException {

      return null;
    }

    /**
     *      int)
     */
    @Override
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

    /**
     *      java.lang.String)
     */
    @Override
    public void getValues( List<String> list, String key ) {

      // getValues unimplemented
    }

    @Override
    public List<String> getValues( String key ) {

      return null;
    }

    @Override
    public Iterator<Configuration> iterator() throws ConfigurationException {

      return null;
    }

    @Override
    public Iterator<Configuration> iterator( String key )
        throws ConfigurationException {

      return null;
    }

    @Override
    public void setConfigurationLoader( ConfigurationLoader loader ) {

      // setConfigurationLoader unimplemented
    }

  };

  /**
   * The field {@code resourceFinder} contains the test finder.
   */
  private final ResourceFinder resourceFinder = new ResourceFinder() {

    @Override
    public void enableTracing( boolean flag ) {


    }

    /**
     *      java.lang.String)
     */
    @Override
    public NamedInputStream findResource( String name, String type )
        throws ConfigurationException {

      return new NamedInputStream(
          new ByteArrayInputStream( "".getBytes() ), "test" );
    }
  };

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    BibReaderFactory factory =
        new BibReaderFactory( CFG, resourceFinder, null, null );
    BibReader ret = factory.newInstance( "abc" );
    assertNotNull( ret );
    assertTrue( ret instanceof BibReader099Impl );
    assertNull( factory.getEncoding() );
  }

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    BibReaderFactory factory =
        new BibReaderFactory( CFG, resourceFinder, null, "ASCII" );
    BibReader ret = factory.newInstance( "abc" );
    assertNotNull( ret );
    assertTrue( ret instanceof BibReader099Impl );
    assertEquals( "ASCII", factory.getEncoding() );
  }

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    BibReaderFactory factory =
        new BibReaderFactory( CFG, resourceFinder, "ASCII", "Latin1" );
    BibReader ret = factory.newInstance( "abc" );
    assertNotNull( ret );
    assertTrue( ret instanceof BibReader099Impl );
    assertEquals( "ASCII", factory.getEncoding() );
  }

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    BibReaderFactory factory =
        new BibReaderFactory( CFG, resourceFinder, null, null );
    factory.setEncoding( "ASCII" );
    assertEquals( "ASCII", factory.getEncoding() );
  }

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test(expected = IllegalArgumentException.class)
  public void testError1() throws Exception {

    new BibReaderFactory( CFG, null, null, null ).newInstance( "abc" );
  }

  /**
   * TOOD
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationUnsupportedEncodingException.class)
  public void testError2() throws Exception {

    new BibReaderFactory( CFG, resourceFinder, null, "en" )
        .newInstance( "abc" );
  }

}
