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

package org.extex.exbib.core.io.bblio;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is a test suite for {@link BblWriterFactory}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BblWriterFactoryTest {

  private static final String DIR_TARGET = "build";

  /**
   * The object under test.
   */
  private class Testee extends BblWriterFactory {

    /**
     * Creates a new object.
     *
     * @param configuration the configuration
     * @param encoding      the encoding
     * @throws ConfigurationException       in case of a configuration error
     * @throws UnsupportedEncodingException in case of an encoding error
     */
    public Testee( Configuration configuration, String encoding )
        throws ConfigurationException,
        UnsupportedEncodingException {

      super( configuration, encoding );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoDiscarted()
     */
    @Override
    protected void infoDiscarted() {

      dis = true;
      super.infoDiscarted();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoOutput(java.lang.String)
     */
    @Override
    protected void infoOutput( String file ) {

      out = true;
      super.infoOutput( file );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoStdout()
     */
    @Override
    protected void infoStdout() {

      std = true;
      super.infoStdout();
    }
  }

  /**
   * The field {@code CFG} contains the configuration.
   */
  private static final Configuration CFG = new Configuration() {

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
     */
    @Override
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getAttribute(java.lang.String)
     */
    @Override
    public String getAttribute( String name ) {

      if( "class".equals( name ) ) {
        return BblWriter.class.getName();
      }

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String)
     */
    @Override
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getValue()
     */
    @Override
    public String getValue() throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getValue(java.lang.String)
     */
    @Override
    public String getValue( String key ) throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getValueAsInteger(java.lang.String,
     *      int)
     */
    @Override
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
     *      java.lang.String)
     */
    @Override
    public void getValues( List<String> list, String key ) {

    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
     */
    @Override
    public List<String> getValues( String key ) {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#iterator()
     */
    @Override
    public Iterator<Configuration> iterator() throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
     */
    @Override
    public Iterator<Configuration> iterator( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)
     */
    @Override
    public void setConfigurationLoader( ConfigurationLoader loader ) {

    }
  };

  /**
   * The field {@code dis} contains the indicator that discarded output has
   * been recognized.
   */
  private boolean dis;

  /**
   * The field {@code out} contains the indicator that file output has been
   * recognized
   */
  private boolean out;

  /**
   * The field {@code std} contains the indicator that standard output has
   * been recognized
   */
  private boolean std;

  /**
   * Test that a new instance can be obtained.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewInstance1() throws Exception {

    Writer w = new BblWriterFactory( CFG, null ).newInstance( null );
    assertNotNull( w );
    w.close();
  }

  /**
   * Test that the discarded notifier is triggered and none of the others
   * when invoked with null as argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewInstance2() throws Exception {

    dis = false;
    out = false;
    std = false;
    Writer w = new Testee( CFG, null ).newInstance( null );
    assertNotNull( w );
    assertTrue( dis );
    assertFalse( out );
    assertFalse( std );
    w.close();
  }

  /**
   * Test that the discarded notifier is triggered and none of the others
   * when invoked with the empty string as argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewInstance3() throws Exception {

    dis = false;
    out = false;
    std = false;
    Writer w = new Testee( CFG, null ).newInstance( "" );
    assertNotNull( w );
    assertTrue( dis );
    assertFalse( out );
    assertFalse( std );
    w.close();
  }

  /**
   * Test that the stdout notifier is triggered and none of the others when
   * invoked with the string "-" as argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewInstance4() throws Exception {

    dis = false;
    out = false;
    std = false;
    Writer w = new Testee( CFG, null ).newInstance( "-" );
    assertNotNull( w );
    assertFalse( dis );
    assertFalse( out );
    assertTrue( std );
    w.close();
  }

  /**
   * Test that the stdout notifier is triggered and none of the others when
   * invoked with the string "-" as argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNewInstance5() throws Exception {

    dis = false;
    out = false;
    std = false;
    Writer w = new Testee( CFG, null ).newInstance( DIR_TARGET + "/test" );
    assertNotNull( w );
    assertFalse( dis );
    assertTrue( out );
    assertFalse( std );
    w.close();
  }

}
