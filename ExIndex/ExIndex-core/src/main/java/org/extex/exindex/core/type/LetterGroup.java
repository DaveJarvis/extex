/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type;

import org.extex.exindex.core.exception.IndexerException;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.markup.Markup.Position;
import org.extex.exindex.core.type.markup.MarkupTransform;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.core.type.transform.Transform;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * This class represents a letter group.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LetterGroup {

  /**
   * The field {@code map} contains the mapping from name to index entry.
   */
  private final Map<String, IndexEntry> map = new HashMap<String, IndexEntry>();

  /**
   * The field {@code after} contains the the letter groups preceding this
   * one.
   */
  private final List<LetterGroup> after = new ArrayList<LetterGroup>();

  /**
   * The field {@code before} contains the letter groups following this
   * one. This value is cleared after the collecting phase.
   */
  private final List<LetterGroup> before = new ArrayList<LetterGroup>();

  /**
   * The field {@code name} contains the name of the letter group.
   */
  private final String name;

  /**
   * Creates a new object.
   *
   * @param name the name
   */
  public LetterGroup( String name ) {

    this.name = name;
  }

  /**
   * Place the constraint on this letter group to ge after another one.
   *
   * @param g the letter group to go before
   */
  public void after( LetterGroup g ) {

    after.add( g );
    g.before.add( this );
  }

  /**
   * Remove a letter group from the after list.
   *
   * @param letterGroup the letter group to remove
   */
  protected void clearAfter( LetterGroup letterGroup ) {

    after.remove( letterGroup );
  }

  /**
   * Clear all after groups of the before groups. This method is meant for
   * internal purposes. Use it on your own risk.
   */
  public void clearAfterBefore() {

    for( LetterGroup g : before ) {
      g.clearAfter( this );
    }
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
   * Get an arbitrary element from the before list or {@code null} if
   * this list is empty.
   *
   * @return an arbitrary element from the before list or {@code null}
   * if this list is empty
   */
  public LetterGroup getSomeAfter() {

    if( after.isEmpty() ) {
      return null;
    }
    return after.get( 0 );
  }

  /**
   * Get an arbitrary element from the before list or {@code null} if
   * this list is empty.
   *
   * @return an arbitrary element from the before list or {@code null}
   * if this list is empty
   */
  public LetterGroup getSomeBefore() {

    if( before.isEmpty() ) {
      return null;
    }
    return before.get( 0 );
  }

  /**
   * Check whether some elements are contained.
   *
   * @return {@code true} iff the letter group does not contain
   * elements
   * @see java.util.List#isEmpty()
   */
  public boolean isEmpty() {

    return map.isEmpty();
  }

  /**
   * Check that a certain markup position is present. This means that the
   * value is not {@code null} and not the empty string.
   *
   * @param markup the markup
   * @param pos    the position
   * @return {@code true} iff the markup is not empty
   */
  private boolean markupIsNotEmpty( MarkupTransform markup, Position pos ) {

    String value = markup.get( name, pos );
    return (value != null && !"".equals( value ));
  }

  /**
   * Getter for the sorted list of keys of index entries.
   *
   * @return the sorted list of keys of index entries
   */
  private SortedSet<String> sortedKeys() {

    SortedSet<String> set = new TreeSet<String>();
    set.addAll( map.keySet() );

    return set;
  }

  /**
   * Store a raw index entry.
   *
   * @param raw   the raw entry to store
   * @param depth the hierarchy depth
   * @throws IndexerException in case of an error
   */
  public void store( RawIndexentry raw, int depth ) throws IndexerException {

    String[] sortKey = raw.getSortKey();
    IndexEntry entry = map.get( sortKey[ 0 ] );
    if( entry == null ) {
      entry = new IndexEntry( raw, sortKey[ 0 ] );
      map.put( sortKey[ 0 ], entry );
    }
    entry.store( sortKey, 1, depth, raw );
  }

  @Override
  public String toString() {

    return name + ": " + super.toString();
  }

  /**
   * Check that there is only one after group and return it. If there are none
   * or more than one then return {@code null}
   *
   * @return the unique after group or null
   */
  public LetterGroup uniqueAfter() {

    return after.size() != 1 ? null : after.get( 0 );
  }

  /**
   * Write the letter group to a writer.
   *
   * @param writer          the writer
   * @param interpreter     the interpreter
   * @param markupContainer the container for markup information
   * @param trace           the indicator for tracing
   * @return {@code true} iff something has been written
   * @throws IOException               in case of an I/O error
   * @throws LNonMatchingTypeException in case of an error
   */
  public boolean write( Writer writer, LInterpreter interpreter,
                        MarkupContainer markupContainer, boolean trace )
      throws IOException,
      LNonMatchingTypeException {

    SortedSet<String> sortedKeys = sortedKeys();
    if( sortedKeys.isEmpty() ) {
      return false;
    }

    Markup m = markupContainer.getMarkup( "markup-letter-group" );
    MarkupTransform markup;
    if( m instanceof MarkupTransform ) {
      markup = (MarkupTransform) m;
    }
    else {
      markup = new MarkupTransform( "markup-letter-group" );
    }

    boolean first = true;
    markup.write( writer, markupContainer, name, Markup.OPEN, trace );

    if( markupIsNotEmpty( markup, Markup.OPEN_HEAD )
        || markupIsNotEmpty( markup, Markup.CLOSE_HEAD ) ) {
      markup
          .write( writer, markupContainer, name, Markup.OPEN_HEAD, trace );
      Transform trans = markup.getTransform( name );
      writer.write( trans == null ? name : trans.transform( name ) );
      markup.write( writer, markupContainer, name, Markup.CLOSE_HEAD,
                    trace );
    }

    for( String key : sortedKeys ) {
      IndexEntry entry = map.get( key );
      if( entry == null ) {
        continue;
      }
      if( first ) {
        first = false;
      }
      else {
        markup.write( writer, markupContainer, name, Markup.SEP, trace );
      }

      entry.write( writer, interpreter, markupContainer, trace, 0 );
    }
    markup.write( writer, markupContainer, name, Markup.CLOSE, trace );

    return first;
  }

}
