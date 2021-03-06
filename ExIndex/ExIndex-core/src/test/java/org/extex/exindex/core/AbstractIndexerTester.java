/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core;

import org.extex.exindex.core.parser.exindex.ExIndexParserFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static org.junit.Assert.assertEquals;

/**
 * This is a test driver for the {@link Indexer}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractIndexerTester {

  /**
   * Dummy implementation for testing purposes.
   */
  private static final class MyFinder implements ResourceFinder {

    public void enableTracing( boolean flag ) {

      // no effect
    }

    /**
     * java.lang.String)
     */
    public NamedInputStream findResource( String name, String type )
        throws ConfigurationException {

      String s = FILES.get( name );
      return s == null ? null : new NamedInputStream(
          new ByteArrayInputStream( s.getBytes() ), "" );
    }
  }

  /**
   * The field {@code FILES} contains the prerecorded resources.
   */
  private static final Map<String, String> FILES =
      new HashMap<>();

  /**
   * The field {@code FINDER} contains the resource finder.
   */
  private static final ResourceFinder FINDER = new ResourceFinder() {

    public void enableTracing( boolean flag ) {

      // nay
    }

    /**
     *      java.lang.String)
     */
    public NamedInputStream findResource( String name, String type )
        throws ConfigurationException {

      String content = FILES.get( name );
      return content == null ? null : new NamedInputStream(
          new ByteArrayInputStream( content.getBytes() ), "" );
    }

  };

  /**
   * Add a resource
   *
   * @param name     the name
   * @param contents the contents
   */
  public static void register( String name, String contents ) {

    FILES.put( name, contents );
  }

  /**
   * Create a List of Strings and fill it with some values.
   *
   * @param args the varargs of the values
   * @return the List constructed. This is never {@code null}
   */
  public List<String> makeList( String... args ) {

    ArrayList<String> list = new ArrayList<>();
    Collections.addAll( list, args );
    return list;
  }

  /**
   * Run a test and compare the output and error stream results.
   *
   * @param styles      the styles to pass in
   * @param resources   the resources to read
   * @param expectedOut the expected output
   * @param expectedLog the expected logging output
   * @throws Exception in case of an error
   */
  protected void runTest( List<String> styles, List<String> resources,
                          String expectedOut, String expectedLog )
      throws Exception {

    Locale.setDefault( Locale.ENGLISH );

    Logger logger = Logger.getLogger( "test" );
    logger.setUseParentHandlers( false );
    ByteArrayOutputStream log = new ByteArrayOutputStream();
    Handler handler = new StreamHandler( log, new LogFormatter() );
    handler.setLevel( Level.WARNING );
    logger.addHandler( handler );

    Indexer indexer = new Indexer();
    ResourceFinder finder = new MyFinder();
    indexer.setResourceFinder( finder );
    ExIndexParserFactory parserFactory = new ExIndexParserFactory();
    parserFactory.setResourceFinder( FINDER );
    indexer.setParserFactory( parserFactory );
    StringWriter writer = (expectedOut == null ? null : new StringWriter());
    indexer.run( styles, resources, writer, logger );

    if( expectedLog != null ) {
      log.close();
      handler.flush();
      handler.close();
      assertEquals( "log", expectedLog, log.toString() );
    }

    if( writer != null ) {
      String s = writer.toString();
      assertEquals( expectedOut, s );
    }
  }

}
