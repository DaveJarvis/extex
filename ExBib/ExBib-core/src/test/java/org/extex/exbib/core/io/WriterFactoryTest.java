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

package org.extex.exbib.core.io;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This is a test suite for a {@link WriterFactory}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class WriterFactoryTest {

  private static final String DIR_TARGET = "build";

  /**
   * Test configuration.
   */
  private static class MyConfig implements Configuration {

    /**
     * The field {@code encoding} contains the encoding.
     */
    private final String encoding;

    /**
     * Creates a new object.
     *
     * @param encoding the encoding
     */
    public MyConfig( String encoding ) {

      this.encoding = encoding;
    }

    /**
     * java.lang.String)
     */
    public Configuration findConfiguration( String key )
        throws ConfigurationInvalidResourceException,
        ConfigurationNotFoundException,
        ConfigurationSyntaxException,
        ConfigurationIOException {

      return null;
    }

    /**
     * java.lang.String, java.lang.String)
     */
    public Configuration findConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String)
     */
    public String getAttribute( String name ) {

      if( "encoding".equals( name ) ) {
        return encoding;
      }
      return null;
    }

    /**
     * java.lang.String)
     */
    public Configuration getConfiguration( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String, java.lang.String)
     */
    public Configuration getConfiguration( String key, String attribute )
        throws ConfigurationException {

      return null;
    }

    public String getValue() throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String)
     */
    public String getValue( String key ) throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String, int)
     */
    public int getValueAsInteger( String key, int defaultValue )
        throws ConfigurationException {

      return 0;
    }

    /**
     * java.util.List, java.lang.String)
     */
    public void getValues( List<String> list, String key ) {


    }

    /**
     * java.lang.String)
     */
    public List<String> getValues( String key ) {

      return null;
    }

    public Iterator<Configuration> iterator() throws ConfigurationException {

      return null;
    }

    /**
     * java.lang.String)
     */
    public Iterator<Configuration> iterator( String key )
        throws ConfigurationException {

      return null;
    }

    /**
     * org.extex.framework.configuration.ConfigurationLoader)
     */
    public void setConfigurationLoader( ConfigurationLoader loader ) {


    }
  }

  /**
   * {@link WriterFactory#configure(Configuration)} recognizes an unsupported
   * encoding
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationUnsupportedEncodingException.class)
  public final void testConfigureConfiguration() throws Exception {

    new WriterFactory( new MyConfig( null ) ).configure( new MyConfig( "xyzzy"
    ) );
  }

  /**
   * {@link WriterFactory#newInstance()} returns a {@link NullWriter}
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstance() throws Exception {

    assertTrue( new WriterFactory( new MyConfig( null ) ).newInstance() instanceof NullWriter );
  }

  /**
   * A {@code null} argument leads to a {@link NullWriter}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstancePrintStream0() throws Exception {

    assertTrue( new WriterFactory( new MyConfig( null ) )
                    .newInstance( (PrintStream) null ) instanceof NullWriter );
  }

  /**
   * A non-{@code null} argument leads to some writer.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstancePrintStream1() throws Exception {

    PrintStream ps = new PrintStream( new ByteArrayOutputStream() );
    assertFalse( new WriterFactory( new MyConfig( null ) ).newInstance( ps ) instanceof NullWriter );
  }

  /**
   * A {@code null} argument leads to an {@link NullWriter}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceString0() throws Exception {

    assertTrue( new WriterFactory( new MyConfig( null ) )
                    .newInstance( (String) null ) instanceof NullWriter );
  }

  /**
   * A non-{@code null} argument leads to an exception if the file can not be
   * opened
   *
   * @throws Exception in case of an error
   */
  @Test(expected = FileNotFoundException.class)
  public final void testNewInstanceString1() throws Exception {

    new WriterFactory( new MyConfig( null ) )
        .newInstance( DIR_TARGET + "/does/not/exist" );
  }

  /**
   * A non-{@code null} argument opens a file for writing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceString2() throws Exception {

    File file = new File( DIR_TARGET, "writer.test" );
    if( file.exists() ) {
      assertTrue( file.delete() );
    }
    Writer writer =
        new WriterFactory( new MyConfig( null ) ).newInstance( file
                                                                   .toString() );
    assertNotNull( writer );
    writer.close();
    if( file.exists() ) {
      assertTrue( file.delete() );
    }
    else {
      fail( file.toString() + " has not been created" );
    }
  }

  /**
   * A non-{@code null} argument leads to some writer.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceStringBuffer() throws Exception {

    assertFalse( new WriterFactory( new MyConfig( null ) )
                     .newInstance( new StringBuffer() ) instanceof NullWriter );
  }

  /**
   * Two {@code null} writers lead to an {@link NullWriter}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceWriterWriter0() throws Exception {

    assertTrue( new WriterFactory( new MyConfig( null ) )
                    .newInstance( null, null ) instanceof NullWriter );
  }

  /**
   * One {@code null} writer lead to the other writer.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceWriterWriter1() throws Exception {

    Writer w = new NullWriter();
    assertEquals( w, new WriterFactory( new MyConfig( null ) ).newInstance( w,
                                                                            null ) );
  }

  /**
   * One {@code null} writer lead to the other writer.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceWriterWriter2() throws Exception {

    Writer w = new NullWriter();
    assertEquals( w,
                  new WriterFactory( new MyConfig( null ) ).newInstance( null,
                                                                         w ) );
  }

  /**
   * One {@code null} writer lead to the other writer.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testNewInstanceWriterWriter3() throws Exception {

    Writer w = new NullWriter();
    Writer writer = new WriterFactory( new MyConfig( null ) ).newInstance( w,
                                                                           w );
    assertNotSame( w, writer );
    assertTrue( writer instanceof MultiWriter );
  }

  /**
   * {@code null} is a legal value for the encoding.
   *
   * @throws Exception in case of an error
   */
  @Test
  public final void testSetEncoding0() throws Exception {

    new WriterFactory( new MyConfig( null ) ).setEncoding( null );
  }

  /**
   * An unknown encoding leads to an Exception.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = UnsupportedEncodingException.class)
  public final void testSetEncoding1() throws Exception {

    new WriterFactory( new MyConfig( null ) ).setEncoding( "xyzzy" );
  }

  /**
   * A {@code null} argument leads to a {@link NullPointerException}
   *
   * @throws Exception in case of an error
   */
  @Test(expected = NullPointerException.class)
  public final void testWriterFactory1() throws Exception {

    new WriterFactory( null );
  }

  /**
   * An unknown encoding leads to an Exception.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ConfigurationUnsupportedEncodingException.class)
  public final void testWriterFactory2() throws Exception {

    new WriterFactory( new MyConfig( "xyzzy" ) );
  }

}
