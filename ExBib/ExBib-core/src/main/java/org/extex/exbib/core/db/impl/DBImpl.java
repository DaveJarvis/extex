/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.impl;

import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.sorter.CodepointIgnoreCaseSorter;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.Observable;
import org.extex.exbib.core.util.Observer;
import org.extex.exbib.core.util.ObserverList;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is a simple implementation of a B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;
 * margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X compatible database.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DBImpl implements DB, Configurable, Observable {

  /**
   * The field {@code bibReaderFactory} contains the factory used to get new
   * Readers.
   */
  private BibReaderFactory bibReaderFactory;

  /**
   * The field {@code entries} contains the list of entries to preserve the
   * original order.
   */
  private List<Entry> entries = new ArrayList<>();

  /**
   * The field {@code entryHash} contains the Hash of entries to get fast
   * access.
   */
  private final Map<String, Entry> entryHash = new HashMap<>();

  /**
   * The field {@code strings} contains the defined strings.
   */
  private final Map<String, Value> strings = new HashMap<>();

  /**
   * The field {@code makeAliasHook} contains the observers for the creation
   * of an alias.
   */
  private final ObserverList makeAliasHook = new ObserverList();

  /**
   * The field {@code makeEntryHook} contains the observers for the creation
   * of an entry.
   */
  private final ObserverList makeEntryHook = new ObserverList();

  /**
   * The field {@code makeMacroHook} contains the observers for the creation
   * of a macro.
   */
  private final ObserverList makeMacroHook = new ObserverList();

  /**
   * The field {@code makePreambleHook} contains the observers for the
   * creation of a preamble.
   */
  private final ObserverList makePreambleHook = new ObserverList();

  /**
   * The field {@code makeStringHook} contains the observers for the creation
   * of a string.
   */
  private final ObserverList makeStringHook = new ObserverList();

  /**
   * The field {@code sorter} contains the sorter to use.
   */
  private Sorter sorter = null;

  /**
   * The field {@code preamble} contains the collected preambles.
   */
  private final Value preamble = new Value();

  /**
   * The field {@code minCrossref} contains the minimum number of crossrefs
   * to leave the cross-referenced entry alone; otherwise it is inlined.
   */
  private int minCrossrefs = 2;

  /**
   * Create a new empty database.
   */
  public DBImpl() {

  }

  public void configure( Configuration config ) throws ConfigurationException {

    sorter = new CodepointIgnoreCaseSorter();
  }

  /**
   * Getter for the list of entries stored in the database.
   *
   * @return the list of entries
   */
  public List<Entry> getEntries() {

    return entries;
  }

  /**
   * Get a single entry stored under the given reference key. The key is
   * normalized before the entry is sought, i.e. the search is case
   * insensitive.
   * <p>
   * If no record is stored under the given key then {@code null} is
   * returned.
   *
   * @param key the reference key
   * @return the record or {@code null}
   */
  public Entry getEntry( String key ) {

    return entryHash.get( key.toLowerCase( Locale.ENGLISH ) );
  }

  public String getExpandedMacro( String key ) {

    Value value = getMacro( key );

    return value == null ? null : value.expand( this );
  }

  public Value getMacro( String name ) {

    return strings.get( name.toLowerCase( Locale.ENGLISH ) );
  }

  public List<String> getMacroNames() {

    return new ArrayList<>( strings.keySet() );
  }

  /**
   * Getter for minCrossrefs.
   *
   * @return the minCrossref
   */
  public int getMinCrossrefs() {

    return minCrossrefs;
  }

  /**
   * Getter for the preamble.
   *
   * @return the preamble
   */
  public Value getPreamble() {

    return preamble;
  }

  public String getPreambleExpanded() {

    return preamble.expand( this );
  }

  public Sorter getSorter() {

    return sorter;
  }

  /**
   * Inline all information from one entry to another entry.
   *
   * @param entry the entry to be preserved
   * @param xref  the entry to be inlined
   */
  private void inline( Entry entry, Entry xref ) {

    for( String x : xref.getKeys() ) {
      Value v = xref.get( x );
      if( v != null && entry.get( x ) == null ) {
        entry.set( x, v );
      }
    }
  }

  public Iterator<Entry> iterator() {

    return entries.iterator();
  }

  public List<String> load( String file, Map<String, String> citation )
      throws ExBibException,
      ConfigurationException,
      FileNotFoundException {

    BibReader reader = bibReaderFactory.newInstance( file );

    reader.load( this );

    List<String> missing = new ArrayList<>();

    if( citation == null || citation.containsKey( "*" ) ) {
      return missing;
    }

    Map<String, List<Entry>> cite = new HashMap<>();
    List<String> stack = new ArrayList<>( citation.keySet() );

    List<Entry> newEntries = new ArrayList<>();

    do {
      List<String> open = new ArrayList<>();
      for( String key : stack ) {
        key = key.toLowerCase( Locale.ENGLISH );
        Entry entry = getEntry( key );

        if( entry == null ) {
          missing.add( key );
        }
        else {
          newEntries.add( entry );
          Value xref = entry.get( "crossref" );
          if( xref != null ) {
            String crossref =
                xref.expand( this ).toLowerCase( Locale.ENGLISH );
            List<Entry> xr = cite.get( crossref );
            if( xr == null ) {
              xr = new ArrayList<>();
              cite.put( crossref, xr );
              open.add( crossref );
            }
            xr.add( entry );
          }
        }
      }

      stack = open;
    } while( !stack.isEmpty() );

    for( Map.Entry<String, List<Entry>> x : cite.entrySet() ) {

      List<Entry> v = x.getValue();
      if( v.size() < minCrossrefs ) {
        Entry xref = getEntry( x.getKey() );
        for( Entry entry : v ) {
          inline( entry, xref );
          entry.set( "crossref", (Value) null );
        }
        newEntries.remove( xref );
      }
    }

    entries = newEntries;

    return missing;
  }

  /**
   * Creates a new entry for this database. Usually no Entry should be created
   * with the constructor. This method is preferred since it links the Entry
   * into the database correctly.
   *
   * @param type    the type for the new entry
   * @param key     the key for the new entry
   * @param locator the locator from the user's perspective
   * @return a new entry
   */
  public Entry makeEntry( String type, String key, Locator locator ) {

    Entry entry = new Entry( locator );
    entry.setKey( key );
    entry.setType( type );
    entries.add( entry );
    entryHash.put( key.toLowerCase( Locale.ENGLISH ), entry );
    makeEntryHook.update( this, entry );

    return entry;
  }

  public void registerObserver( String name, Observer observer )
      throws NotObservableException {

    if( "makeAlias".equals( name ) ) {
      makeAliasHook.add( observer );
    }
    else if( "makeMacro".equals( name ) ) {
      makeMacroHook.add( observer );
    }
    else if( "makePreamble".equals( name ) ) {
      makePreambleHook.add( observer );
    }
    else if( "makeEntry".equals( name ) ) {
      makeEntryHook.add( observer );
    }
    else if( "makeString".equals( name ) ) {
      makeStringHook.add( observer );
    }
    else {
      throw new NotObservableException( name );
    }
  }

  /**
   * Setter for the reader factory.
   *
   * @param factory the new value
   */
  public void setBibReaderFactory( BibReaderFactory factory ) {

    bibReaderFactory = factory;
  }

  public void setMinCrossrefs( int minCrossref ) {

    this.minCrossrefs = minCrossref;
  }

  /**
   * Setter for the sorter.
   *
   * @param sorter the new {@link org.extex.exbib.core.db.sorter.Sorter
   *               Sorter}
   */
  public void setSorter( Sorter sorter ) {

    this.sorter = sorter;
  }

  /**
   * Sort the database according to the configured sorter.
   *
   * @throws ConfigurationException if no sorter has been configured jet
   */
  public void sort() throws ConfigurationException {

    if( sorter == null ) {
      throw new ConfigurationMissingException( "sorter" );
    }

    sorter.sort( entries );
  }

  /**
   * Creates an alias for an existing entry. The alias is a means to access
   * the entry under a different name. Nevertheless changes to one affects
   * both of them.
   *
   * @param alias   the key for the new entry
   * @param key     the key for the existing entry to link to
   * @param locator the locator
   * @throws ExBibEntryUndefinedException in case that the key can not be
   *                                      resolved
   */
  public void storeAlias( String alias, String key, Locator locator )
      throws ExBibEntryUndefinedException {

    Entry entry = getEntry( key );

    if( entry == null ) {
      throw new ExBibEntryUndefinedException( key, locator );
    }

    entryHash.put( alias, entry );
    makeAliasHook.update( this, alias );
  }

  /**
   * Store the given comment in the database.
   *
   * @param comment the comment to store
   */
  public void storeComment( String comment ) {

    // not yet
  }

  /**
   * Store the given preamble in the database.
   *
   * @param pre the preamble to store
   */
  public void storePreamble( Value pre ) {

    preamble.add( pre );
    makePreambleHook.update( this, pre );
  }

  /**
   * Store a string definition in the database.
   *
   * @param name  the name of the string definition. The name is not case
   *              sensitive. Thus it is translated to lower case internally
   * @param value the value of the string definition
   */
  public void storeString( String name, Value value ) {

    strings.put( name.toLowerCase( Locale.ENGLISH ), value );
    makeStringHook.update( this, name );
  }
}
