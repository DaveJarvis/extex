/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core;

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.DBFactory;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observer;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is a container for a named set of bibliographies.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ProcessorContainer
    implements
    Configurable,
    ResourceAware,
    Iterable<String> {

  /**
   * This is a pair of name and observer.
   */
  private static class NamedObserver {

    /**
     * The field {@code name} contains the name.
     */
    private final String name;

    /**
     * The field {@code observer} contains the observer.
     */
    private final Observer observer;

    /**
     * Creates a new object.
     *
     * @param name     the name
     * @param observer the observer
     */
    public NamedObserver( String name, Observer observer ) {

      this.name = name;
      this.observer = observer;
    }

    /**
     * Getter for name.
     *
     * @return the name
     */
    public String getName() {

      return name;
    }

    /**
     * Getter for observer.
     *
     * @return the observer
     */
    public Observer getObserver() {

      return observer;
    }

  }

  /**
   * < The field {@code obsList} contains the observers.
   */
  private final List<NamedObserver> obsList = new ArrayList<NamedObserver>();

  /**
   * The field {@code dbObsList} contains the observers.
   */
  private final List<NamedObserver> dbObsList =
      new ArrayList<NamedObserver>();

  /**
   * The field {@code bibliographies} contains the bibliographies.
   */
  private final Map<String, Processor> bibliographies =
      new HashMap<String, Processor>();

  /**
   * The field {@code dbFactory} contains the database factory.
   */
  private DBFactory dbFactory;

  /**
   * The field {@code processorFactory} contains the processor factory.
   */
  private ProcessorFactory processorFactory;

  /**
   * The field {@code bibReaderFactory} contains the bib reader factory.
   */
  private BibReaderFactory bibReaderFactory;

  /**
   * The field {@code minCrossrefs} contains the {@code min.crossrefs}
   * parameter for the database.
   */
  private int minCrossrefs = 2;

  /**
   * The field {@code fallback} contains the default name for a processor.
   */
  private String fallback;

  /**
   * The field {@code sorterFactory} contains the sorterFactory.
   */
  private SorterFactory sorterFactory;

  /**
   * The field {@code logger} contains the logger.
   */
  private final Logger logger;

  /**
   * The field {@code properties} contains the properties.
   */
  private final Properties properties;

  /**
   * The field {@code finder} contains the resource finder.
   */
  private ResourceFinder finder;

  /**
   * Creates a new object.
   *
   * @param config     the configuration
   * @param logger     the logger
   * @param properties the properties
   * @throws ExBibMissingNumberException in case of a numeric option with a
   *                                     non-numeric value
   */
  public ProcessorContainer( Configuration config, Logger logger,
                             Properties properties )
      throws ExBibMissingNumberException {

    configure( config );
    this.logger = logger;
    this.properties = properties;
    try {
      minCrossrefs =
          Integer.parseInt( properties.getProperty(
              ExBib.PROP_MIN_CROSSREF,
              Integer.toString( minCrossrefs ) ) );
    } catch( NumberFormatException e ) {
      throw new ExBibMissingNumberException( properties.getProperty(
          ExBib.PROP_MIN_CROSSREF, Integer.toString( minCrossrefs ) ), null );
    }
  }

  public void configure( Configuration config ) throws ConfigurationException {

    fallback = config.getAttribute( "fallback" );

    dbFactory = new DBFactory( config.getConfiguration( "DB" ) );
    processorFactory =
        new ProcessorFactory( config.getConfiguration( "Processor" ) );
  }

  /**
   * Find or create a bibliography for a given key.
   *
   * @param key the key for the bibliography to find; the value
   *            {@code null} denotes the default bibliography
   * @return the bibliography for the key. This is guaranteed to be not
   * {@code null}
   * @throws ExBibException               in case of an error
   * @throws ConfigurationException       in case of a configuration error
   * @throws IOException                  in case of an I/O error
   * @throws CsfException                 in case of a problem with the csf
   * @throws UnsupportedEncodingException in case of a problem with the
   *                                      encoding
   */
  public Processor findProcessor( String key )
      throws ConfigurationException,
      ExBibException,
      UnsupportedEncodingException,
      CsfException,
      IOException {

    String name = (key == null ? fallback : key);

    Processor processor = bibliographies.get( name );
    if( processor == null ) {

      int mx = minCrossrefs;
      String p = properties.getProperty(
          ExBib.PROP_MIN_CROSSREF + "." + name );
      if( p != null ) {
        try {
          mx = Integer.parseInt( p );
        } catch( NumberFormatException e ) {
          throw new ExBibMissingNumberException( p, null );
        }
      }

      DB db = dbFactory.newInstance( bibReaderFactory, mx );

      String s = properties.getProperty( ExBib.PROP_SORT + "." + name );
      if( s == null ) {
        s = properties.getProperty( ExBib.PROP_SORT );
      }
      Sorter sorter = sorterFactory.newInstance( s );

      if( sorter != null ) {
        db.setSorter( sorter );
      }
      processor =
          processorFactory.newInstance( properties
                                            .getProperty( ExBib.PROP_PROCESSOR ),
                                        db );
      processor.setLogger( logger );
      if( processor instanceof ResourceAware ) {
        ((ResourceAware) processor).setResourceFinder( finder );
      }

      try {
        for( NamedObserver no : dbObsList ) {
          db.registerObserver( no.getName(), no.getObserver() );
        }

        for( NamedObserver no : obsList ) {
          processor.registerObserver( no.getName(), no.getObserver() );
        }
        prepareProcessor( processor, db );
      } catch( NotObservableException e ) {
        throw new ExBibException( e );
      }

      bibliographies.put( name, processor );
    }
    return processor;
  }

  /**
   * Getter for minCrossrefs.
   *
   * @return the minCrossrefs
   */
  public int getMinCrossrefs() {

    return minCrossrefs;
  }

  /**
   * Getter for a named processor.
   *
   * @param key the key
   * @return the bibliography or {@code null}
   */
  public Processor getProcessor( String key ) {

    return bibliographies.get( key == null ? fallback : key );
  }

  /**
   * Check whether the container is empty.
   *
   * @return {@code true} if the container is empty
   */
  public boolean isEmpty() {

    return bibliographies.isEmpty();
  }

  public Iterator<String> iterator() {

    return bibliographies.keySet().iterator();
  }

  /**
   * This method is invoked when a new Processor has been made. It is meant to
   * be overwritten in a derived class.
   *
   * @param processor the processor
   * @param db        its database
   * @throws NotObservableException     in case of an error
   * @throws ExBibIllegalValueException in case of an error
   */
  protected void prepareProcessor( Processor processor, DB db )
      throws NotObservableException,
      ExBibIllegalValueException {

    // overwrite me
  }

  /**
   * Registers an observer for the database.
   *
   * @param name     the name of the action to register for
   * @param observer the observer to invoke upon the action
   * @throws NotObservableException in case that the name does not correspond
   *                                to an observable action
   */
  public void registerDbObserver( String name, Observer observer )
      throws NotObservableException {

    dbObsList.add( new NamedObserver( name, observer ) );
  }

  /**
   * Registers an observer.
   *
   * @param name     the name of the action to register for
   * @param observer the observer to invoke upon the action
   * @throws NotObservableException in case that the name does not correspond
   *                                to an observable action
   */
  public void registerObserver( String name, Observer observer )
      throws NotObservableException {

    obsList.add( new NamedObserver( name, observer ) );
  }

  /**
   * Setter for bibReaderFactory.
   *
   * @param bibReaderFactory the bibReaderFactory to set
   */
  public void setBibReaderFactory( BibReaderFactory bibReaderFactory ) {

    this.bibReaderFactory = bibReaderFactory;
  }

  /**
   * Setter for minCrossrefs.
   *
   * @param minCrossrefs the minCrossrefs to set
   */
  public void setMinCrossrefs( int minCrossrefs ) {

    this.minCrossrefs = minCrossrefs;
  }

  /**
   * Set an option for one or all processors.
   *
   * @param type the type of the processor
   * @param arg  the name and value
   * @throws ExBibException         in case of an error
   * @throws ConfigurationException in case of a configuration error
   */
  public void setOption( String type, String arg )
      throws ConfigurationException,
      ExBibException {

    int i = arg.indexOf( '=' );
    String key;
    String value;
    if( i < 0 ) {
      key = arg;
      value = "true";
    }
    else {
      key = arg.substring( 0, i );
      value = arg.substring( i + 1 );
    }

    properties.put( key + "." + type, value );

    try {
      Processor processor = findProcessor( type );

      if( "sort".equals( key ) ) {
        processor.getDB().setSorter( sorterFactory.newInstance( value ) );
      }
      else {
        processor.setOption( key, value );
      }
    } catch( UnsupportedEncodingException e ) {
      throw new ConfigurationWrapperException( e );
    } catch( CsfException e ) {
      throw new ConfigurationWrapperException( e );
    } catch( IOException e ) {
      throw new ConfigurationWrapperException( e );
    }
  }

  public void setResourceFinder( ResourceFinder f ) {

    this.finder = f;
  }

  /**
   * Setter for sorterFactory.
   *
   * @param sorterFactory the sorterFactory to set
   */
  public void setSorterFactory( SorterFactory sorterFactory ) {

    this.sorterFactory = sorterFactory;
  }

  @Override
  public String toString() {

    return bibliographies.toString();
  }

}
