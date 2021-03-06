/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration;

import org.extex.framework.configuration.exception.*;
import org.extex.framework.configuration.impl.XmlConfiguration;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Test suite for the configuration factory.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ConfigurationFactoryTest {

  /**
   * TODO gene: missing JavaDoc.
   */
  public class Xxx implements Configuration {

    /**
     * Creates a new object.
     *
     * @param resource the resource
     */
    public Xxx( String resource ) {

    }

    @Override
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return null;
    }

    /**
     * java.lang.String)
     */
    @Override
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    @Override
    public String getAttribute( String name ) {

      return null;
    }

    @Override
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String)
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
     * int)
     */
    @Override
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

    /**
     * java.lang.String)
     */
    @Override
    public void getValues( List<String> list, String key ) {

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

    }
  }

  /**
   * TODO gene: missing JavaDoc.
   */
  public class Yyy extends Xxx {

    /**
     * Creates a new object.
     *
     * @param resource the resource
     */
    public Yyy( String resource ) {

      super( resource );
      throw new NullPointerException();
    }

  }


  public ConfigurationFactoryTest() {

    Locale.setDefault( Locale.ENGLISH );
  }

  /**
   * Test that null leads to an appropriate error message.
   */
  @Test(expected = ConfigurationInvalidResourceException.class)
  public void testNewInstance01() {

    System.clearProperty( "Util.Configuration.class" );
    ConfigurationFactory.newInstance( null );
  }

  /**
   * Test that the empty string leads to an appropriate error message
   */
  @Test(expected = ConfigurationInvalidResourceException.class)
  public void testNewInstance02() {

    System.clearProperty( "Util.Configuration.class" );
    ConfigurationFactory.newInstance( "" );
  }

  /**
   * Test that an undefined configuration leads to an appropriate error message
   */
  @Test(expected = ConfigurationNotFoundException.class)
  public void testNewInstance03() {

    System.clearProperty( "Util.Configuration.class" );
    ConfigurationFactory.newInstance( "undefined" );
  }

  /**
   * Test that an invalid configuration leads to an appropriate error message
   * <p>
   * Note: Redirecting the Error stream is necessary to get rid of irritating
   * messages on stderr.
   */
  @Test(expected = ConfigurationSyntaxException.class)
  public void testNewInstance04() {

    PrintStream err = System.err;
    System.setErr( new PrintStream( new OutputStream() {

      @Override
      public void write( int b ) throws IOException {

      }
    } ) );
    try {
      System.clearProperty( "Util.Configuration.class" );
      ConfigurationFactory
          .newInstance(
              "org/extex/framework/configuration/EmptyConfiguration.xml" );
    } finally {
      System.setErr( err );
    }
  }

  /**
   * Test that a valid configuration is loaded.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test
  public void testNewInstance10() throws ConfigurationException {

    System.clearProperty( "Util.Configuration.class" );
    Configuration cfg =
        ConfigurationFactory
            .newInstance( "org/extex/framework/configuration/Configuration" +
                              ".xml" );
    assertNotNull( cfg );
    assertEquals( "", cfg.getValue() );
    assertFalse( cfg.iterator().hasNext() );
  }

  /**
   * Test that a valid configuration is loaded.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test
  public void testNewInstance11() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class",
                        XmlConfiguration.class.getName() );
    Configuration cfg =
        ConfigurationFactory
            .newInstance( "org/extex/framework/configuration/Configuration" +
                              ".xml" );
    assertNotNull( cfg );
    assertEquals( "", cfg.getValue() );
    assertFalse( cfg.iterator().hasNext() );
  }

  /**
   * Test that an invalid class name leads to an error.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test(expected = ConfigurationClassNotFoundException.class)
  public void testNewInstance12() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class", "xyzzy" );
    ConfigurationFactory
        .newInstance( "org/extex/framework/configuration/Configuration.xml" );
  }

  /**
   * Test that an invalid class name leads to an error.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test(expected = ConfigurationInstantiationException.class)
  public void testNewInstance13() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class", "java.lang.String" );
    ConfigurationFactory
        .newInstance( "org/extex/framework/configuration/Configuration.xml" );
  }

  /**
   * Test that a valid configuration is loaded.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test(expected = ConfigurationInvalidResourceException.class)
  public void testNewInstance14() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class",
                        XmlConfiguration.class.getName() );
    ConfigurationFactory.newInstance( null );
  }

  /**
   * Test that an invalid class leads to an error.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test(expected = ConfigurationInstantiationException.class)
  public void testNewInstance15() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class", Xxx.class.getName() );
    ConfigurationFactory
        .newInstance( "org/extex/framework/configuration/Configuration.xml" );
  }

  /**
   * Test that an invalid class leads to an error.
   *
   * @throws ConfigurationException in case of an error
   */
  @Test(expected = ConfigurationInstantiationException.class)
  public void testNewInstance16() throws ConfigurationException {

    System.setProperty( "Util.Configuration.class", Yyy.class.getName() );
    ConfigurationFactory
        .newInstance( "org/extex/framework/configuration/Configuration.xml" );
  }

}
