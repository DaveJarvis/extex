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

package org.extex.exbib.bsf;

import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.*;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link BsfProcessor}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BsfProcessorTest {

  /**
   * A built-in implementation of a Configuration for testing.
   */
  private static class Cfg implements Configuration {

    /**
     * The field {@code engine} contains the name of the engine.
     */
    private final String engine;

    /**
     * The field {@code extensions} contains the list of extensions.
     */
    private final String extensions;

    /**
     * The field {@code script} contains the name of the script.
     */
    private final String script;

    /**
     * Creates a new object.
     *
     * @param script     the name of the script
     * @param engine     the name of the engine
     * @param extensions the list of extensions
     */
    public Cfg( String script, String engine, String extensions ) {

      this.script = script;
      this.engine = engine;
      this.extensions = extensions;
    }

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

      if( "script".equals( name ) ) {
        return script;
      }
      if( "engine".equals( name ) ) {
        return engine;
      }
      if( "extensions".equals( name ) ) {
        return extensions;
      }
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

      return "";
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
    public void getValues( List<String> list, String key )
        throws IllegalArgumentException {

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

      return new Iterator<Configuration>() {

        @Override
        public boolean hasNext() {

          return false;
        }

        @Override
        public Configuration next() {

          return null;
        }

        @Override
        public void remove() {

        }
      };
    }

    @Override
    public void setConfigurationLoader( ConfigurationLoader loader ) {

    }
  }

  /**
   * The default constructor treats the database an logger to be
   * null.
   */
  @Test
  public void testBsfProcessor() {

    BsfProcessor p = new BsfProcessor();
    assertNotNull( p );
    assertNull( p.getDB() );
    assertNull( p.getLogger() );
  }

  /**
   * A null value for database and logger in the constructor is stored
   * properly and can be retrieved
   */
  @Test
  public void testBsfProcessor10() {

    BsfProcessor p = new BsfProcessor( null, null );
    assertNotNull( p );
    assertNull( p.getDB() );
    assertNull( p.getLogger() );
  }

  /**
   * A non-null value for database and logger in the constructor is stored
   * properly and can be retrieved
   */
  @Test
  public void testBsfProcessor11() {

    Logger logger = Logger.getAnonymousLogger();
    DBImpl db = new DBImpl();
    BsfProcessor p = new BsfProcessor( db, logger );
    assertNotNull( p );
    assertEquals( db, p.getDB() );
    assertEquals( logger, p.getLogger() );
  }

  /**
   * A null configuration leads to a {@link NullPointerException}.
   */
  @Test(expected = NullPointerException.class)
  public void testConfigure01() {

    BsfProcessor p = new BsfProcessor();
    p.configure( null );
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure02() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( null, null, null ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "script", e.getAttribute() );
      throw e;
    }
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure03() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( null, "", null ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "script", e.getAttribute() );
      throw e;
    }
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure04() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( null, null, "" ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "script", e.getAttribute() );
      throw e;
    }
  }

  /**
   * An empty engine in the configuration leads to a
   * {@link ConfigurationMissingAttributeException}
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure05() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( null, "", "" ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "script", e.getAttribute() );
      throw e;
    }
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure06() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( "", null, null ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "engine", e.getAttribute() );
      throw e;
    }
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure07() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( "", "", null ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "extensions", e.getAttribute() );
      throw e;
    }
  }

  /**
   * A missing engine or a missing extensions value in the
   * configuration leads to a {@link ConfigurationMissingAttributeException}.
   */
  @Test(expected = ConfigurationMissingAttributeException.class)
  public void testConfigure08() {

    BsfProcessor p = new BsfProcessor();
    try {
      p.configure( new Cfg( "", null, "" ) );
    } catch( ConfigurationMissingAttributeException e ) {
      assertEquals( "engine", e.getAttribute() );
      throw e;
    }
  }

  /**
   * An empty engine in the configuration leads to a
   * {@link ConfigurationMissingAttributeException}
   */
  @Test
  public void testConfigure10() {

    BsfProcessor p = new BsfProcessor();
    p.configure( new Cfg( "", "", "" ) );
  }

  /**
   * Macro names without a database is the empty list.
   */
  @Test
  public void testGetMacroNames1() {

    BsfProcessor p = new BsfProcessor();
    List<String> list = p.getMacroNames();
    assertNotNull( list );
    assertEquals( 0, list.size() );
  }

  /**
   * Macro names with an empty database is the empty list.
   */
  @Test
  public void testGetMacroNames2() {

    Logger logger = Logger.getAnonymousLogger();
    DBImpl db = new DBImpl();
    BsfProcessor p = new BsfProcessor( db, logger );
    List<String> list = p.getMacroNames();
    assertNotNull( list );
    assertEquals( 0, list.size() );
  }

  /**
   * Macro names with an non-empty database is the list from the database
   */
  @Test
  public void testGetMacroNames3() {

    Logger logger = Logger.getAnonymousLogger();
    DBImpl db = new DBImpl();
    db.storeString( "abc", null );
    BsfProcessor p = new BsfProcessor( db, logger );
    List<String> list = p.getMacroNames();
    assertNotNull( list );
    assertEquals( 1, list.size() );
  }

  /**
   * The number of warnings is initially 0.
   */
  @Test
  public void testGetNumberOfWarnings0() {

    BsfProcessor p = new BsfProcessor();
    assertEquals( 0, p.getNumberOfWarnings() );
  }

  /**
   * The number of warnings is 1 after the first warning.
   */
  @Test
  public void testGetNumberOfWarnings1() {

    BsfProcessor p = new BsfProcessor();
    p.warning( "" );
    assertEquals( 1, p.getNumberOfWarnings() );
  }

  /**
   * Initially the output writer is null.
   */
  @Test
  public void testGetOutWriter() {

    BsfProcessor p = new BsfProcessor();
    assertNull( p.getOutWriter() );
  }

  /**
   * The entry type "" is known for the BsfProcessor.
   */
  @Test
  public void testIsKnown() {

    BsfProcessor p = new BsfProcessor();
    assertTrue( p.isKnown( "" ) );
  }

  /**
   * BsfProcessor.process(null returns 0.
   *
   * @throws ExBibException in case of an error
   */
  @Test
  public void testProcess1() throws ExBibException {

    BsfProcessor p = new BsfProcessor();
    assertEquals( 0, p.process( null ) );
  }

  /**
   * An unknown bibliography database leads to a
   * ExBibFileNotFoundException if everything else is set up correctly.
   *
   * @throws ExBibException in case of an error
   */
  @Test(expected = ExBibFileNotFoundException.class)
  public void testProcess2() throws ExBibException {

    DBImpl db = new DBImpl();
    db.setBibReaderFactory( new BibReaderFactory( null, null, "", "" ) {

      @Override
      public BibReader newInstance( String file )
          throws ConfigurationException,
          FileNotFoundException {

        throw new FileNotFoundException( file );
      }
    } );
    BsfProcessor p = new BsfProcessor( db, Logger.getAnonymousLogger() );
    p.addBibliographyDatabase( "xyzzy" );
    p.process( null );
  }

  /**
   * An empty language leads to an exception.
   *
   * @throws Exception in case of an error
   */
  @Test(expected = ExBibException.class)
  public void testProcess3() throws Exception {

    PrintStream err = System.err;
    System.setErr( new PrintStream( new OutputStream() {

      @Override
      public void write( int b ) throws IOException {

      }
    }, true, "UTF-8" ) );
    try {
      DBImpl db = new DBImpl();
      BsfProcessor p = new BsfProcessor( db, null );
      p.configure( new Cfg( "", "", "" ) );
      p.setResourceFinder( new ResourceFinder() {

        @Override
        public void enableTracing( boolean flag ) {

        }

        @Override
        public NamedInputStream findResource( String name, String type )
            throws ConfigurationException {

          return new NamedInputStream( new ByteArrayInputStream( "zzz"
                                                                     .getBytes() ),
                                       "xxx" );
        }
      } );
      p.addBibliographyStyle( "xyzzy" );
      p.process( null );
    } finally {
      System.setErr( err );
    }
  }

  /**
   * A warning is counted even if it is not logged since no logger
   * is present.
   */
  @Test
  public void testWarning0() {

    BsfProcessor p = new BsfProcessor();
    p.warning( "abc" );
    assertEquals( 1, p.getNumberOfWarnings() );
  }

  /**
   * A warning is counted and logged.
   */
  @Test
  public void testWarning1() {

    Logger logger = Logger.getLogger( getClass().getName() );
    logger.setUseParentHandlers( false );
    for( Handler h : logger.getHandlers() ) {
      logger.removeHandler( h );
    }
    logger.setLevel( Level.WARNING );
    final StringBuilder sb = new StringBuilder();
    Handler logAdaptor = new Handler() {

      @Override
      public void close() throws SecurityException {

      }

      @Override
      public void flush() {

      }

      @Override
      public void publish( LogRecord record ) {

        sb.append( record.getMessage() );
      }
    };
    logger.addHandler( logAdaptor );
    try {
      BsfProcessor p = new BsfProcessor( new DBImpl(), logger );
      p.warning( "abc" );
      assertEquals( 1, p.getNumberOfWarnings() );
    } finally {
      logger.removeHandler( logAdaptor );
    }
    assertEquals( "abc\n", sb.toString() );
  }

}
