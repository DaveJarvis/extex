/*
 * Copyright (C) 2010-2011 The ExTeX Group and individual authors listed below
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link MultiConfiguration}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MultiConfigurationTest {

  /**
   * The field {@code CFG1} contains the first base configuration.
   */
  private static final Configuration CFG1 = new Configuration() {

    @Override
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return "s".equals( key ) ? this : null;
    }

    @Override
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return "s".equals( key ) && "a".equals( attribute ) ? this : null;
    }

    @Override
    public String getAttribute( String name ) {

      return "a".equals( name ) ? "A" : "X";
    }

    @Override
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      Configuration c = findConfiguration( key );
      if( c == null ) {
        throw new ConfigurationNotFoundException( key, null );
      }
      return c;
    }

    @Override
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      Configuration c = findConfiguration( key, attribute );
      if( c == null ) {
        throw new ConfigurationNotFoundException( key, attribute );
      }
      return c;
    }

    @Override
    public String getValue() throws ConfigurationException {

      return "val";
    }

    @Override
    public String getValue( String key ) throws ConfigurationException {

      return "val";
    }

    @Override
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 42;
    }

    @Override
    public void getValues( List<String> list, String key ) {

    }

    @Override
    public List<String> getValues( String key ) {

      return new ArrayList<String>();
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
  };

  /**
   * The field {@code mc0} contains the multi-configuration without
   * sub-configurations.
   */
  private MultiConfiguration mc0;

  /**
   * The field {@code mc1} contains the multi-configuration with one
   * sub-configuration.
   */
  private MultiConfiguration mc1;

  /**
   * The field {@code mc1} contains the multi-configuration with two
   * sub-configurations.
   */
  private MultiConfiguration mc2;

  /**
   * Create the test objects.
   *
   * @throws Exception in case of an error
   */
  @Before
  public void setUp() throws Exception {

    mc0 = new MultiConfiguration( new ArrayList<Configuration>() );

    ArrayList<Configuration> a = new ArrayList<Configuration>();
    a.add( CFG1 );
    mc1 = new MultiConfiguration( a );

    a = new ArrayList<Configuration>();
    a.add( CFG1 );
    a.add( CFG1 );
    mc2 = new MultiConfiguration( a );
  }

  /**
   * Release the test objects.
   *
   * @throws Exception in case of an error
   */
  @After
  public void tearDown() throws Exception {

    mc0 = null;
    mc1 = null;
    mc2 = null;
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationString0() {

    assertNull( mc0.findConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationString1() {

    assertNull( mc1.findConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationString11() {

    assertNotNull( mc1.findConfiguration( "s" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationString2() {

    assertNull( mc2.findConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationString21() {

    assertNotNull( mc2.findConfiguration( "s" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationStringString0() {

    assertNull( mc0.findConfiguration( "", "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationStringString1() {

    assertNull( mc1.findConfiguration( "", "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationStringString11() {

    assertNotNull( mc1.findConfiguration( "s", "a" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationStringString2() {

    assertNull( mc2.findConfiguration( "", "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#findConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testFindConfigurationStringString21() {

    assertTrue( mc2.findConfiguration( "s",
                                       "a" ) instanceof MultiConfiguration );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getAttribute(java.lang.String)}
   * .
   */
  @Test
  public void testGetAttribute0() {

    assertNull( mc0.getAttribute( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getAttribute(java.lang.String)}
   * .
   */
  @Test
  public void testGetAttribute1() {

    assertEquals( "X", mc1.getAttribute( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationString0() {

    assertNull( mc0.getConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationString1() {

    assertNull( mc1.getConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationString11() {

    assertNotNull( mc1.getConfiguration( "s" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationString2() {

    assertNull( mc2.getConfiguration( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationString21() {

    assertNotNull( mc2.getConfiguration( "s" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test(expected = ConfigurationNotFoundException.class)
  public void testGetConfigurationStringString0() {

    mc0.getConfiguration( "", "" );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test(expected = ConfigurationNotFoundException.class)
  public void testGetConfigurationStringString1() {

    mc1.getConfiguration( "", "" );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationStringString11() {

    assertNotNull( mc1.getConfiguration( "s", "a" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getConfiguration(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testGetConfigurationStringString21() {

    assertNotNull( mc2.getConfiguration( "s", "a" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValue()}
   * .
   */
  @Test
  public void testGetValue0() {

    assertEquals( "", mc0.getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValue()}
   * .
   */
  @Test
  public void testGetValue1() {

    assertEquals( "val", mc1.getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValueAsInteger(java.lang.String, int)}
   * .
   */
  @Test
  public void testGetValueAsInteger0() {

    assertEquals( 42, mc0.getValueAsInteger( "", 42 ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValueAsInteger(java.lang.String, int)}
   * .
   */
  @Test
  public void testGetValueAsInteger1() {

    assertEquals( 42, mc1.getValueAsInteger( "", 24 ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValues(java.util.List, java.lang.String)}
   * .
   */
  @Test
  public void testGetValuesListOfStringString0() {

    ArrayList<String> list = new ArrayList<String>();
    mc0.getValues( list, "x" );
    assertEquals( 0, list.size() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValues(java.util.List, java.lang.String)}
   * .
   */
  @Test
  public void testGetValuesListOfStringString1() {

    ArrayList<String> list = new ArrayList<String>();
    mc1.getValues( list, "x" );
    assertEquals( 0, list.size() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValues(java.lang.String)}
   * .
   */
  @Test
  public void testGetValuesString0() {

    assertEquals( 0, mc0.getValues( "" ).size() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValues(java.lang.String)}
   * .
   */
  @Test
  public void testGetValuesString1() {

    assertEquals( 0, mc1.getValues( "" ).size() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValue(java.lang.String)}
   * .
   */
  @Test
  public void testGetValueString0() {

    assertNull( mc0.getValue( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#getValue(java.lang.String)}
   * .
   */
  @Test
  public void testGetValueString1() {

    assertEquals( "val", mc1.getValue( "" ) );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#iterator()}
   * .
   */
  @Test
  public void testIterator0() {

    Iterator<Configuration> it = mc0.iterator();
    assertNotNull( it );
    assertFalse( it.hasNext() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#iterator()}
   * .
   */
  @Test
  public void testIterator1() {

    Iterator<Configuration> it = mc1.iterator();
    assertNotNull( it );
    assertFalse( it.hasNext() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#iterator(java.lang.String)}
   * .
   */
  @Test
  public void testIteratorString0() {

    Iterator<Configuration> it = mc0.iterator( "x" );
    assertNotNull( it );
    assertFalse( it.hasNext() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#iterator(java.lang.String)}
   * .
   */
  @Test
  public void testIteratorString1() {

    Iterator<Configuration> it = mc1.iterator( "x" );
    assertNotNull( it );
    assertFalse( it.hasNext() );
  }

  /**
   * Test method for
   * {@link org.extex.framework.configuration.impl.MultiConfiguration#MultiConfiguration(java.util.List)}
   * .
   */
  @Test(expected = NullPointerException.class)
  public void testMultiConfiguration1() {

    new MultiConfiguration( null );
  }

}
