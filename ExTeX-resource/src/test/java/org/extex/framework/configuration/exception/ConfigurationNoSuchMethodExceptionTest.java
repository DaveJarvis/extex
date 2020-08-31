/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration.exception;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link ConfigurationNoSuchMethodExceptionTest}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ConfigurationNoSuchMethodExceptionTest {

  /**
   * The field {@code CONF} contains the configuration for testing purposes.
   */
  private static final Configuration CONF = new Configuration() {

    @Override
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return null;
    }

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

    @Override
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

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

    @Override
    public String toString() {

      return "THE CONFIG";
    }

  };

  /**
   * ...
   */
  @Test(expected = NullPointerException.class)
  public void test1() {

    new ConfigurationNoSuchMethodException( "abc", null );
  }

  /**
   * ...
   */
  @Test
  public void testGetLocalizedMessage0() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `' not found", e.getLocalizedMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetLocalizedMessage1() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( "abc", CONF );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `abc' not found in THE CONFIG",
                  e.getLocalizedMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetLocalizedMessage4() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( new Exception( "abc" ) );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `abc' not found\n\tcaused by abc",
                  e.getLocalizedMessage() );
  }

  /**
   * ...
   */
  @Test
  public void testGetLocalizer() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( null );
    assertNotNull( e.getLocalizer() );
  }

  /**
   * ...
   */
  @Test
  public void testGetSource() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( null );
    assertNull( e.getSource() );
  }

  /**
   * ...
   */
  @Test
  public void testGetText1() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( "abc", CONF );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `abc' not found", e.getText() );
  }

  /**
   * ...
   */
  @Test
  public void testGetText2() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( null, CONF );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `' not found", e.getText() );
  }

  /**
   * ...
   */
  @Test
  public void testGetText3() {

    ConfigurationNoSuchMethodException e =
        new ConfigurationNoSuchMethodException( null );
    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Method `' not found", e.getText() );
  }

}
